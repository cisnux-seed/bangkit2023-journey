package com.dicoding.mysamplelogin.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.core.SessionManager
import com.dicoding.core.UserRepository
import com.dicoding.mysamplelogin.chat.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = UserRepository(sesi = SessionManager(context = this))
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvChat = binding.tvChat
        tvChat.text = getString(R.string.hello_welcome_to_chat_feature, userRepository.getUser())
    }
}