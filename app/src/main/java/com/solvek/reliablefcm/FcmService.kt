package com.solvek.reliablefcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.solvek.reliablefcm.utils.formatTime
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag(TAG).d("Firebase token:$token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        val reliableId = data["reliableId"]
        val time = reliableId!!.toLong().formatTime()
        Timber.tag(TAG).i("========= Received a push (id $reliableId, type ${data["type"]} time $time priority ${message.priority})")
        if (data.getBool("network", true)){
            networkOperation()
        }
    }

    private fun networkOperation(){
        Timber.tag(TAG).i("Making web request")
        val url = URL("https://reliablefcm.web.app/simple.txt")
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        val responseCode = con.responseCode
        Timber.tag(TAG).i("Response Code :: $responseCode. Reading content")
        if (responseCode != HttpURLConnection.HTTP_OK) {
            return
        }
        val reader =
            BufferedReader(InputStreamReader(con.inputStream))
        var inputLine: String?
        val response = StringBuffer()
        while (reader.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        reader.close()
        Timber.tag(TAG).i("Data is read. Content '$response'")
    }

    private fun Map<String, String>.getBool(key: String, default: Boolean): Boolean {
        val v = this[key] ?: return default
        return v.equals("true", ignoreCase = true)
    }

    companion object {
        private const val TAG = "FcmService"
    }
}