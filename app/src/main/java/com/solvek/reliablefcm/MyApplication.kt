package com.solvek.reliablefcm

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.solvek.reliablefcm.data.AppDatabase
import timber.log.Timber

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initLogging()
        initFirebase()
    }

    private val db by lazy {Room.databaseBuilder(
            this,
            AppDatabase::class.java, "reliable.db"
        ).build()}

    private val Context.db
        get() = (this.applicationContext as MyApplication).db

    val Context.logDao
        get() = db.logDao()

    private fun initFirebase() {
        Timber.tag(TAG).d("Firebase app initializing")
        FirebaseApp.initializeApp(this)

        Timber.tag(TAG).d("Requesting firebase token")
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                Timber.tag(TAG).i("Firebase token:${it.result}")
                return@addOnCompleteListener
            }

            Timber.tag(TAG).e("Failed to determine firebase token. Exception: ${it.exception}")
        }
    }

    private fun initLogging() {
        Timber.plant(DbLogger(logDao))

        Timber.tag(TAG).d("========================================")
        Timber.tag(TAG).d("Logging initialized")
    }

    companion object {
        private const val TAG = "MyApplication"
    }
}