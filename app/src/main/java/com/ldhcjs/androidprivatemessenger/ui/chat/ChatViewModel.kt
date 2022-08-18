package com.ldhcjs.androidprivatemessenger.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity

class ChatViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is chat Fragment"
    }

    private val _rvChatText = MutableLiveData<MutableList<ChatEntity>>().apply {
        value = mutableListOf(ChatEntity("title","msg","name","time","who", "group"))
    }
    val text: LiveData<String> = _text
    val rvChatText: LiveData<MutableList<ChatEntity>> = _rvChatText
}