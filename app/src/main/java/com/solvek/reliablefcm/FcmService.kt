package com.solvek.reliablefcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag(TAG).i("New token received")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.tag(TAG).i("Received a message (id ${message.messageId}, priority ${message.priority}) $message")
    }

    companion object {
        private const val TAG = "FcmService"
    }
}