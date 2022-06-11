package com.example.iswara.ui.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.iswara.R
import com.example.iswara.databinding.ActivityChatbotBinding

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}