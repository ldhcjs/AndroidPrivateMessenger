package com.ldhcjs.androidprivatemessenger

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FirebaseMessageObject {

    @SerializedName("to")
    @Expose
    private var to: String? = null

    @SerializedName("data")
    @Expose
    private var firebaseMessageData: FirebaseMessageData? = null

    fun getTo(): String? {
        return to
    }

    fun setTo(to: String?) {
        this.to = to
    }

    fun getData(): FirebaseMessageData? {
        return firebaseMessageData
    }

    fun setData(firebaseMessageData: FirebaseMessageData?) {
        this.firebaseMessageData = firebaseMessageData
    }

}