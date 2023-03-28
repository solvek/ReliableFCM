package com.solvek.reliablefcm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LogRecord(
    @PrimaryKey val timestamp: Long,
    val text: String
)