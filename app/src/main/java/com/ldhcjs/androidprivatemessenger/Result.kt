package com.ldhcjs.androidprivatemessenger

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Result {
    @SerializedName("message_id")
    @Expose
    private var messageId: String? = null

    fun getMessageId(): String? {
        return messageId
    }

    fun setMessageId(messageId: String?) {
        this.messageId = messageId
    }

}