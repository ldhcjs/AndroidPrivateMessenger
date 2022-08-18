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
import com.ldhcjs.androidprivatemessenger.Util
import com.ldhcjs.androidprivatemessenger.adapter.ChatAdapter
import com.ldhcjs.androidprivatemessenger.databinding.FragmentChatBinding
import com.ldhcjs.androidprivatemessenger.db.ChatDatabase
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity
import com.ldhcjs.androidprivatemessenger.fcm.FirebaseCloudMsgManager
import kotlinx.coroutines.*

class ChatFragment : Fragment() {

    //    private val tag: String = ChatFragment::class.java.simpleName
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var db: ChatDatabase
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        binding = FragmentChatBinding.inflate(layoutInflater)
        binding.rvChat.layoutManager = LinearLayoutManager(context)

        db = ChatDatabase.getInstance(context)!!
        chatAdapter = ChatAdapter(mutableListOf(ChatEntity("title", "msg", "name", "time", "who", "group")))

        chatViewModel.rvChatText.observe(viewLifecycleOwner, Observer {
            coroutineScope.launch {
                chatAdapter = ChatAdapter(performQueryAsync())
                binding.rvChat.adapter = chatAdapter
            }
        })

        // TODO 푸시 메시지 받아 DB에 실시간 추가 부분까지 완료. 역순으로 레이아웃하는 부분 필요
        db.chatDao().selectAllChatAsync().observe(viewLifecycleOwner, Observer {
            if(it.size > 0) {
                chatAdapter.addRecentChat(it[it.size - 1])
                chatAdapter.notifyItemInserted(chatAdapter.itemCount - 1)
                binding.rvChat.scrollToPosition(chatAdapter.itemCount - 1)
            }
        })


        // TODO 이모지에디트텍스트에서 발송 누르면 실제 메시지 보내지는 과정 수정 필요
        binding.tilChat.setStartIconOnClickListener {
            Toast.makeText(context, "ICON", Toast.LENGTH_SHORT).show()
        }
        binding.ibSend.setOnClickListener{

            val hashMap: HashMap<String, String> = HashMap<String, String>()
            hashMap[FirebaseCloudMsgManager.TITLE] = "test title"
            hashMap[FirebaseCloudMsgManager.MSG] = binding.tieChat.text.toString()
            hashMap[FirebaseCloudMsgManager.NAME] = "test msg"
            hashMap[FirebaseCloudMsgManager.TIME] = Util.getCurrentHour()
            hashMap[FirebaseCloudMsgManager.WHO] = "me"
            hashMap[FirebaseCloudMsgManager.GROUP] = "none"
            hashMap[FirebaseCloudMsgManager.TOKEN] = FirebaseCloudMsgManager.tmp_token

            FirebaseCloudMsgManager.sendFcmObj(FirebaseCloudMsgManager.getFcmObj(hashMap))

            val chatData = ChatEntity(
                hashMap[FirebaseCloudMsgManager.TITLE]!!,
                hashMap[FirebaseCloudMsgManager.MSG]!!,
                hashMap[FirebaseCloudMsgManager.NAME]!!,
                hashMap[FirebaseCloudMsgManager.TIME]!!,
                hashMap[FirebaseCloudMsgManager.WHO]!!,
                hashMap[FirebaseCloudMsgManager.GROUP]!!
            )
            val db = ChatDatabase.getInstance(context)
            // 비동기 동작 코루틴 동작
            CoroutineScope(Dispatchers.IO).launch {
                db!!.chatDao().insert(chatData)
//            Log.d(tag, "fcm chatlist " + db.chatDao().selectAllChat())
            }
        }

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