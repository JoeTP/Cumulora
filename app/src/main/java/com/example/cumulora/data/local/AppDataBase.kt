package com.example.cumulora.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cumulora.utils.DATABASE_NAME

//@Database(entities = [], version = 1)
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