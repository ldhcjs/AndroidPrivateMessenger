package com.ldhcjs.androidprivatemessenger.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.ldhcjs.androidprivatemessenger.R
import com.ldhcjs.androidprivatemessenger.adapter.ChatAdapter
import com.ldhcjs.androidprivatemessenger.databinding.FragmentChatBinding
import com.ldhcjs.androidprivatemessenger.db.ChatDatabase
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity
import kotlinx.coroutines.*

class ChatFragment : Fragment() {

    //    private val tag: String = ChatFragment::class.java.simpleName
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var db: ChatDatabase
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var til_chat: TextInputLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        db = ChatDatabase.getInstance(context)!!
        chatAdapter = ChatAdapter(mutableListOf(ChatEntity("title", "msg", "name", "time")))
        til_chat = container!!.findViewById(R.id.til_chat)

        til_chat.setStartIconOnClickListener {
            Toast.makeText(this, "ICON", Toast.LENGTH_SHORT)
        }

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
            if(it.size > 0) {
                chatAdapter.addRecentChat(it[it.size - 1])
                chatAdapter.notifyItemInserted(chatAdapter.itemCount - 1)
                binding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
            }
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