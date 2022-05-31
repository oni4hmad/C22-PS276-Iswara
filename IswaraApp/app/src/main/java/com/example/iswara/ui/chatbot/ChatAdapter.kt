package com.example.iswara.ui.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iswara.databinding.ItemMessageBinding
import java.text.SimpleDateFormat

class ChatAdapter(private val listChat: List<ChatItem>) : RecyclerView.Adapter<ChatAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listChat[position])
    }

    override fun getItemCount(): Int = listChat.size

    inner class ListViewHolder(private var binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatItem) {
            if(chat.isUser) {
                binding.linlayBot.visibility = View.GONE
                binding.tvSenderMsg.text = chat.chat
                binding.linlaySender.setOnClickListener { onItemClickCallback.onItemClicked(chat) }
            } else {
                if (chat.chatId == listChat[0].chatId) {
                    val param = binding.linlayBot.layoutParams as ViewGroup.MarginLayoutParams
                    param.topMargin = 16
                    binding.linlayBot.layoutParams = param
                }
                binding.linlaySender.visibility = View.GONE
                binding.tvBotMsg.text = chat.chat
                binding.linlayBot.setOnClickListener { onItemClickCallback.onItemClicked(chat) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(tanggapan: ChatItem)
    }

}