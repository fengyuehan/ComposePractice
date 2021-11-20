package com.example.composeweather.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityInfo::class] , version = 1)
abstract class WeatherDatabase :RoomDatabase(){
    abstract fun cityInfoDao(): CityInfoDao

    companion object{
        @Volatile
        private var INSTANCE:WeatherDatabase ?= null

        fun getDataBase(context: Context):WeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance  != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,WeatherDatabase::class.java,"paly_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}