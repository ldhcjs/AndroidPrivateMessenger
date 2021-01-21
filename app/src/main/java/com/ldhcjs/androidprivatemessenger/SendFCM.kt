package com.ldhcjs.androidprivatemessenger


import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonParser
import org.json.JSONObject

class SendFCM(private var token: String) {

    fun sendFirebaseMessage() {
// This registration token comes from the client FCM SDKs.
        // This registration token comes from the client FCM SDKs.
        val registrationToken = "YOUR_REGISTRATION_TOKEN"

// See documentation on defining a message payload.

// See documentation on defining a message payload.
        val jsonObject = JSONObject()
        jsonObject.put("to","cTtdUU9QQdCq1CkhtdrBsZ:APA91bFUqtvcHPpRu8OiHyfNmJse-zMAy2gZJtGwpqFbak6qBV8wOUt-IDJ9MFqTs8Cl88CTZYfOW-DbKfV6L0fdWmApoBlsI4gyLBSvc_CQS-zYA7dV4EEkgCHy68eVgLiRUlqVop95")
        jsonObject.put("title", "TEST")
        jsonObject.put("body", "TEST BODY")
        val jsonBody = JsonParser.parseString(jsonObject.toString())

    }
}