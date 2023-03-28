package com.solvek.reliablefcm

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.solvek.reliablefcm.utils.formatTime
import timber.log.Timber

class FcmService : FirebaseMessagingService() {
    private val firestore = Firebase.firestore

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag(TAG).d("Firebase token:$token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        val reliableId = data["reliableId"]
        val time = reliableId!!.toLong().formatTime()
        Timber.tag(TAG).i("Received a message (id $reliableId, type ${data["type"]} time $time priority ${message.priority})")
        uploadPush(reliableId)
    }

    private fun uploadPush(reliableId: String?) {
        val now = System.currentTimeMillis()
        val push = hashMapOf(
            "timestamp" to now,
            "reliableId" to reliableId,
            "time" to now.formatTime()
        )

        firestore.collection("pushes").document(now.toString())
            .set(push)
            .addOnSuccessListener { Timber.tag(TAG).i("Uploaded push info for $reliableId") }
            .addOnFailureListener { e -> Timber.tag(TAG).e(e, "Upload failed") }
    }

    companion object {
        private const val TAG = "FcmService"
    }
}