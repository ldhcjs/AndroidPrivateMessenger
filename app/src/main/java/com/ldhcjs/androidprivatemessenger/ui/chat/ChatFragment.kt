package com.ldhcjs.androidprivatemessenger.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ldhcjs.androidprivatemessenger.adapter.ChatAdpater
import com.ldhcjs.androidprivatemessenger.databinding.FragmentChatBinding
import com.ldhcjs.androidprivatemessenger.db.ChatDatabase
import com.ldhcjs.androidprivatemessenger.db.entity.ChatEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        binding.rvChat.layoutManager = LinearLayoutManager(context)
        val array = arrayOf("aaa", "bbb", "ccc")
        val arr = ArrayList<ChatEntity>()
        arr.add(ChatEntity("name","title","content","profile"))
        binding.rvChat.adapter = ChatAdpater(arr)
        chatViewModel.rvChatText.observe(viewLifecycleOwner, Observer {
            binding.rvChat.adapter = ChatAdpater(it)
        })

        // Room + Singleton + Coroutine
        // 채팅 데이터 추가
        var chatData = ChatEntity("name","title","content","profile")
        val db = ChatDatabase.getInstance(context)
        // 비동기 동작 코루틴 동작
        CoroutineScope(Dispatchers.IO).launch {
            db!!.chatDao().insert(chatData)
        }
        /*
        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
         */

        return binding.root
    }
}