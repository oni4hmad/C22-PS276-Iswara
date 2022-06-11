package com.example.iswara.ui.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iswara.databinding.ItemMsgBotBinding
import com.example.iswara.databinding.ItemMsgUserBinding

class ChatAdapter(private val listChat: List<ChatItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val BOT_MESSAGE_VIEW = 0
        const val USER_MESSAGE_VIEW = 1
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun getItemViewType(position: Int): Int {
        return if(listChat[position].isUser) {
            USER_MESSAGE_VIEW
        } else {
            BOT_MESSAGE_VIEW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 0) {
            val binding = ItemMsgBotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BotMessageItemViewHolder(binding)
        } else {
            val binding = ItemMsgUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserMessageItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(listChat[position].isUser) {
            (holder as UserMessageItemViewHolder).bindUserMsg(position)
        } else {
            (holder as BotMessageItemViewHolder).bindBotMsg(position)
        }
    }

    private inner class BotMessageItemViewHolder(val binding: ItemMsgBotBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindBotMsg(position: Int) {
            if (position == 0) {
                val param = binding.linlayBot.layoutParams as ViewGroup.MarginLayoutParams
                param.topMargin = 16
                binding.linlayBot.layoutParams = param
            }
            val chat = listChat[position]
            binding.tvBotMsg.text = chat.chat
            binding.tvBotMsg.requestLayout()
            binding.linlayBot.setOnClickListener { onItemClickCallback.onItemClicked(chat) }
        }
    }

    private inner class UserMessageItemViewHolder(val binding: ItemMsgUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindUserMsg(position: Int) {
            if (position == 0) {
                val param = binding.linlaySender.layoutParams as ViewGroup.MarginLayoutParams
                param.topMargin = 16
                binding.linlaySender.layoutParams = param
            }
            val chat = listChat[position]
            binding.tvSenderMsg.text = chat.chat
            binding.tvSenderMsg.requestLayout()
            binding.linlaySender.setOnClickListener { onItemClickCallback.onItemClicked(chat) }
        }
    }

    override fun getItemCount(): Int = listChat.size

    interface OnItemClickCallback {
        fun onItemClicked(tanggapan: ChatItem)
    }
}