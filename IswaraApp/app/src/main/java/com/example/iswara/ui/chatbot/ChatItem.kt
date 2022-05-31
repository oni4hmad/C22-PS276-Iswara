package com.example.iswara.ui.chatbot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatItem(
    val chatId: String,
    val isUser: Boolean,
    val chat: String
) : Parcelable
