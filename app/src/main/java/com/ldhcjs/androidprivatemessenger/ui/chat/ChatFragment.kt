package com.ldhcjs.androidprivatemessenger.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ldhcjs.androidprivatemessenger.adapter.ChatAdapter
import com.ldhcjs.androidprivatemessenger.databinding.FragmentChatBinding
import com.ldhcjs.androidprivatemessenger.db.ChatDatabase
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity
import kotlinx.coroutines.*

class ChatFragment : Fragment() {

    //    private val tag: String = ChatFragment::class.java.simpleName
    private lateinit var binding: FragmentChatBinding
    private val chatViewModel: ChatViewModel by viewModels()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val db = ChatDatabase.getInstance(context)
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        binding.rvChat.layoutManager = LinearLayoutManager(context)

        chatViewModel.rvChatText.observe(viewLifecycleOwner, Observer {
            coroutineScope.launch {
                binding.rvChat.adapter = ChatAdapter(performQueryAsync())
            }
        })
        // TODO 푸시 메시지를 서비스에서 받고 채팅창에 실시간 업데이트 가능하도록 이벤트 보내야 함

        return binding.root
    }

    private suspend fun performQueryAsync(): MutableList<ChatEntity> =
        withContext(Dispatchers.IO) {
            Log.d(tag, "performQueryAsync chatlist mutableList")
            return@withContext db!!.chatDao().selectAllChat()
        }
}