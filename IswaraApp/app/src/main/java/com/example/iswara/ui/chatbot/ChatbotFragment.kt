package com.example.iswara.ui.chatbot

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iswara.databinding.FragmentChatbotBinding

class ChatbotFragment : Fragment() {

    private lateinit var binding: FragmentChatbotBinding
    private lateinit var viewModel: ChatbotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ChatbotViewModel::class.java]
        viewModel.listChat.observe(viewLifecycleOwner) { listChat ->
            showRecyclerList(listChat)
        }

        binding.edtChat.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                binding.btnKirim.isEnabled = text.toString().isNotEmpty()
            }
        })
        binding.btnKirim.setOnClickListener {
            val chatToSend = binding.edtChat.text.toString()
            binding.edtChat.setText(String(), TextView.BufferType.EDITABLE)
            viewModel.sendChat(chatToSend)
        }
    }

    private fun showRecyclerList(listChat: ArrayList<ChatItem>) {
        binding.rvChat.layoutManager = LinearLayoutManager(view?.context)

        val listChatAdapter = ChatAdapter(listChat)
        binding.rvChat.adapter = listChatAdapter

        listChatAdapter.setOnItemClickCallback(object : ChatAdapter.OnItemClickCallback {
            override fun onItemClicked(chat: ChatItem) {
                chat.apply { showToast("$id, $isUser, ${this.chat}") }
            }
        })

        /* scroll to top recycler view */
        binding.rvChat.adapter?.itemCount?.also { itemCount ->
            binding.rvChat.scrollToPosition(itemCount-1)
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}