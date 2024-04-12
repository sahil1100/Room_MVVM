package com.example.apprication_task

import android.app.Application
import androidx.room.Room
import com.example.apprication_task.database.UserRoomDatabase

class MyApplication : Application() {
    companion object {
        lateinit var database: UserRoomDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, UserRoomDatabase::class.java, "user_db").build()
    }
}
