package com.example.apprication_task.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apprication_task.dao.UserDao
import com.example.apprication_task.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}