package com.ldhcjs.androidprivatemessenger.fcm

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object FirebaseCloudMsgManager {

    val TAG: String = FirebaseCloudMsgManager::class.java.simpleName

    val TOKEN: String = "token"
    val TITLE: String = "title"
    val BODY: String = "body"

    private val header = HashMap<String, String>()

    var tmp_token: String = ""

    fun init() {
        header["Accept"] = "application/json"
        header["Content-Type"] = "application/json"
        header["Authorization"] = "key=AAAAHMenarM:APA91bGI0pqsVLM_6M3hK6BXGiUE2QBBHvEkBkW-ZA-tU_COZyGv8Cj9y8W403QclEO5eGJfKIC4ZphLeUsCzAk01tro3xBwI6ofi8uWMMhXw7RP3JmVNJQkIQu1fowrXtqWGo44wIDa"
    }

    fun getFcmObj(fcmData: HashMap<String, String>) : FirebaseMessageObject {
        val firebaseMessageObject = FirebaseMessageObject()
        val firebaseMessageData = FirebaseMessageData()
        firebaseMessageData.setTitle(fcmData[TITLE])
        firebaseMessageData.setBody(fcmData[BODY])
        firebaseMessageObject.setTo(fcmData[TOKEN])
        firebaseMessageObject.setData(firebaseMessageData)

        return firebaseMessageObject
    }

    @SuppressLint("CheckResult")
    fun sendFcmObj(fcmObj: FirebaseMessageObject) {
        val okHttpClient = OKHttpManager.getIntance()
        okHttpClient.sendMsg(header, fcmObj)
            .subscribeOn(Schedulers.io()) // subscribe 연산에 사용되는 Thread
            .observeOn(AndroidSchedulers.mainThread()) // subscribe 연산 종료 후 mainThread로 돌려줌
            .subscribe(
                { result ->
                    Log.d(TAG, "result.size : ${result.getSuccess()}")

                },
                { error -> Log.d(TAG, "Error : ${error.localizedMessage}") }
            )
    }
}