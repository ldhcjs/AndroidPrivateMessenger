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
        binding.rvChat.adapter = ChatAdpater(array)
        chatViewModel.rvChatText.observe(viewLifecycleOwner, Observer {
            binding.rvChat.adapter = ChatAdpater(it)
        })

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