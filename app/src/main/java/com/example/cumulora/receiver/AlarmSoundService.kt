package com.example.cumulora.receiver


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cumulora.R

class AlarmService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var audioManager: AudioManager

    override fun onCreate() {
        super.onCreate()
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            "START" -> startAlarm()
            "STOP" -> stopAlarm()
        }
        return START_STICKY
    }

    private fun startAlarm() {
        try {
            stopAlarm()

            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_ALARM)
                setOnPreparedListener {
                    setVolume(1.0f, 1.0f)
                    start()
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("AlarmService", "Error $what, $extra")
                    stopAlarm()
                    false
                }

                val afd = resources.openRawResourceFd(R.raw.alert)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()

                prepareAsync()
                isLooping = true
            }

            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0)

            startForeground(NOTIFICATION_ID, createNotification())
            Log.d("AlarmService", "Alarm started")
        } catch (e: Exception) {
            Log.e("AlarmService", "Failed to start alarm", e)
            stopSelf()
        }
    }

    private fun stopAlarm() {
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null

        audioManager.abandonAudioFocus(null)
        stopForeground(true)
        stopSelf()
        Log.d("AlarmService", "Alarm stopped")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        createNotificationChannel()

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Alarm is playing")
            .setContentText("Tap to stop the alarm")
            .setSmallIcon(R.drawable.calendar)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for alarm notifications"
            }

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        stopAlarm()
        super.onDestroy()
    }

    companion object {
        private const val CHANNEL_ID = "alarm_service_channel"
        private const val NOTIFICATION_ID = 123

        fun startService(context: Context) {
            val intent = Intent(context, AlarmService::class.java).apply {
                action = "START"
            }
            context.startService(intent)
        }

        fun stopService(context: Context) {
            val intent = Intent(context, AlarmService::class.java).apply {
                action = "STOP"
            }
            context.startService(intent)
        }
    }
}