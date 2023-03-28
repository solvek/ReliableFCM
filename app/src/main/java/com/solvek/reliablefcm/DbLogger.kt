package com.solvek.reliablefcm

import android.util.Log
import com.solvek.reliablefcm.data.LogDao
import com.solvek.reliablefcm.data.LogRecord
import timber.log.Timber
import java.util.concurrent.Executors

class DbLogger(private val logDao: LogDao): Timber.DebugTree() {
    private val executor = Executors.newSingleThreadExecutor()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)

        if (priority >= Log.INFO) {
            appendMessage(message)
        }
    }

    private fun appendMessage(message: String) {
        val record = LogRecord(System.currentTimeMillis(), message)
        executor.submit {
            logDao.insertRolling(record)
        }
    }
}