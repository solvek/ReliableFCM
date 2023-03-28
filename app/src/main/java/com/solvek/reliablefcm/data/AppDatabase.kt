package com.solvek.reliablefcm.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LogRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao
}