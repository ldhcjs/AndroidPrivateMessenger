package com.ldhcjs.androidprivatemessenger.fcm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FirebaseMessageData {
    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("body")
    @Expose
    private var body: String? = null

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getBody(): String? {
        return body
    }

    fun setBody(body: String?) {
        this.body = body
    }
}