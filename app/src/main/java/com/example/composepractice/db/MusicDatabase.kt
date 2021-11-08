package com.example.composepractice.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composepractice.data.User

@Database(version = 1,exportSchema = true,entities = [User::class])
abstract class MusicDatabase :RoomDatabase(){
    abstract fun userDao():UserDao
}