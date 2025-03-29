package com.example.cumulora.core.objects

import android.content.Context
import android.media.MediaPlayer
import com.example.cumulora.R

object MyMediaPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun play(context: Context) {


        mediaPlayer = MediaPlayer.create(context, R.raw.alert).apply {
            try {
                isLooping = true
                start()
                setOnErrorListener { mp, what, extra ->
                    mp.release()
                    mediaPlayer = null
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
//                release()
                mediaPlayer = null
            }
        }
    }

    fun stop() {
        mediaPlayer?.let {
            it.stop()
            it.release()
            mediaPlayer = null
        }
    }

}
