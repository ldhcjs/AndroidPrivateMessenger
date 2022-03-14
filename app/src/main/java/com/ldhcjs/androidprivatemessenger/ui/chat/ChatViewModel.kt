package com.ldhcjs.androidprivatemessenger.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is chat Fragment"
    }

    private val _rvChatText = MutableLiveData<Array<String>>().apply {
        value = arrayOf("Init data1", "Init data2", "Init data3")
    }
    val text: LiveData<String> = _text
    val rvChatText: LiveData<Array<String>> = _rvChatText
}