package com.example.cumulora.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cumulora.data.local.weather.SavedWeather
import com.example.cumulora.data.local.weather.SavedWeatherTypeConverter
import com.example.cumulora.data.local.weather.WeatherDao
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.models.alarm.AlarmTypeConverter
import com.example.cumulora.utils.DATABASE_NAME

@Database(entities = [SavedWeather::class, Alarm::class], version = 3)
@TypeConverters(SavedWeatherTypeConverter::class, AlarmTypeConverter::class)
abstract class AppDataBase : RoomDatabase(){

    abstract fun weatherDao(): WeatherDao

    companion object{
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java, DATABASE_NAME
                ).build()
                instance = inst
                inst
            }
        }
    }

}