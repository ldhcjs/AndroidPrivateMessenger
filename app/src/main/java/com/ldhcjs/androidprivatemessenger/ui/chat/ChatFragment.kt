package com.ldhcjs.androidprivatemessenger.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private lateinit var db: ChatDatabase
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        db = ChatDatabase.getInstance(context)!!
        chatAdapter = ChatAdapter(mutableListOf(ChatEntity("title", "msg", "name", "time")))

        binding = FragmentChatBinding.inflate(layoutInflater)
        binding.rvChat.layoutManager = LinearLayoutManager(context)

        chatViewModel.rvChatText.observe(viewLifecycleOwner, Observer {
            coroutineScope.launch {
                chatAdapter = ChatAdapter(performQueryAsync())
                binding.rvChat.adapter = chatAdapter
            }
        })

        // TODO 푸시 메시지 받아 DB에 실시간 추가 부분까지 완료. 역순으로 레이아웃하는 부분 필요
        db.chatDao().selectAllChatAsync().observe(this, Observer {
            chatAdapter.addRecentChat(it[it.size - 1])
            chatAdapter.notifyItemInserted(0)
        })

        return binding.root
    }

    private suspend fun performQueryAsync(): MutableList<ChatEntity> =
        withContext(Dispatchers.IO) {
            Log.d(tag, "performQueryAsync chatlist mutableList")
            return@withContext db.chatDao().selectAllChat()
        }

    private suspend fun performQueryAsync1(): LiveData<MutableList<ChatEntity>> =
        withContext(Dispatchers.IO) {
            Log.d(tag, "performQueryAsync1 chatlist mutableList")
            return@withContext db.chatDao().selectAllChatAsync()
        }
}