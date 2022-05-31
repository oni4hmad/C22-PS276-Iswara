package com.example.iswara.ui.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iswara.data.dummy.DataDummy
import com.example.iswara.ui.ruang_cerita.detail_tanggapan.TanggapanItem
import java.util.*
import kotlin.collections.ArrayList

class ChatbotViewModel : ViewModel() {

    private val _listChat = MutableLiveData<ArrayList<ChatItem>>()
    val listChat: LiveData<ArrayList<ChatItem>> = _listChat

    init {
        /* get dummy chat */
        _listChat.value = DataDummy.getChat(false, 1)
    }

    private fun generateBalasan() {
        val id = _listChat.value?.count().toString()
        DataDummy.getChat(false, 1)[0].let {
            _listChat.value?.let { list ->
                list.add(it)
                _listChat.value = list
            }
        }
    }

    fun sendChat(chat: String) {
        val id = _listChat.value?.count().toString()
        val chatBaru = ChatItem(id, true, chat)
        _listChat.value?.let { list ->
            list.add(chatBaru)
            _listChat.value = list
        } ?: run {
            val temp = ArrayList<ChatItem>()
            temp.add(chatBaru)
            _listChat.value = temp
        }
        generateBalasan()
    }

}