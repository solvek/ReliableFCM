package com.solvek.reliablefcm

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initLogging()
        initFirebase()
    }

    private fun initFirebase() {
        Timber.tag(TAG).d("Firebase app initializing")
        FirebaseApp.initializeApp(this)

        Timber.tag(TAG).d("Requesting firebase token")
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                Timber.tag(TAG).i("Firebase token: ${it.result}")
                return@addOnCompleteListener
            }

            Timber.tag(TAG).e("Failed to determine firebase token. Exception: ${it.exception}")
        }
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())

        Timber.tag(TAG).d("========================================")
        Timber.tag(TAG).d("Logging initialized")
    }

    companion object {
        private const val TAG = "MyApplication"
    }
}