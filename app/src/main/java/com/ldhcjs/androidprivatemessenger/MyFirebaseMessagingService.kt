package com.ldhcjs.androidprivatemessenger

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val tag = MyFirebaseMessagingService::class.java.simpleName

    override fun onNewToken(token: String) {
        Log.d(tag, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(tag, "Received msg: " + p0.data)
        super.onMessageReceived(p0)
    }
}