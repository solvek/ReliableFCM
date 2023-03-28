package com.solvek.reliablefcm.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {
    @Transaction
    fun insertRolling(vararg records: LogRecord){
        insert(*records)
        val lastToSurvive = get(1, 100)
        if (lastToSurvive.isEmpty()) return

        clean(lastToSurvive[0].timestamp)
    }

    @Query("DELETE FROM LogRecord WHERE `timestamp`<:olderThan")
    fun clean(olderThan: Long)

    @Query("SELECT * FROM LogRecord ORDER BY timestamp DESC LIMIT :limit")
    fun get(limit: Int): Flow<List<LogRecord>>

    @Query("SELECT * FROM LogRecord ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    fun get(limit: Int, offset: Int): List<LogRecord>

    @Insert
    fun insert(vararg records: LogRecord)
}