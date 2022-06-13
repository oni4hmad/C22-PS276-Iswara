package com.example.iswara.ui.chatbot

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iswara.data.database.Chat
import com.example.iswara.data.preferences.Session
import com.example.iswara.data.preferences.SessionPreference
import com.example.iswara.databinding.FragmentChatbotBinding
import com.example.test_tflite_app_simple.chatbot.Chatbot
import com.example.test_tflite_app_simple.chatbot.InputFormat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ChatbotFragment : Fragment() {

    private lateinit var binding: FragmentChatbotBinding
    private lateinit var viewModel: ChatbotViewModel
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var mChatbot: Chatbot
    private lateinit var session: Session
    private var dialog: MaterialAlertDialogBuilder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Ara (Chatbot)"
        }

        SessionPreference(view.context).getSession()?.also {
            session = it
        }

        mChatbot = Chatbot(view.context)
        mChatbot.setOnChatbotResponded { respond, input, inputFormat ->
            setInputUI(view.context, input, inputFormat)
            lifecycleScope.launch(Dispatchers.IO) {
                val botState = mChatbot.getRemainingIntentClassesJson()
                viewModel.insertOrUpdateBotState(botState)
                viewModel.sendBotChat(respond)
                when (input) {
                    Chatbot.Input.ENDED -> viewModel.setBotStateEnded()
                    else -> {}
                }
            }
        }

        viewModel = ViewModelProvider(this, ChatbotViewModelModelFactory(view.context, session))[ChatbotViewModel::class.java]
        arguments?.let { arg ->
            ChatbotFragmentArgs.fromBundle(arg as Bundle).laporan?.also {
                viewModel.setReport(it)
                if (it.isFinish) fixJustUIEnded()
                viewModel.getChatHistory().observe(viewLifecycleOwner) {
                    viewModel.getChatHistory().removeObservers(viewLifecycleOwner)
                    viewModel.getChatHistory().observe(viewLifecycleOwner) { listChat ->
                        Log.d("ChatbotFragment", listChat.joinToString())
                        if (listChat.isNotEmpty()) showRecyclerList(listChat)
                    }
                    showLoading(false)
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.getBotState()?.let { state ->
                        Log.d("ChatbotFragment", state.remainClassJson)
                        mChatbot.setRemainingIntentClasses(state.remainClassJson)
                        if (state.isEnded) {
                            withContext(Dispatchers.Main) { setToEnded(true) }
                        }
                    }
                }
            }
        } ?: run {
            viewModel.getOnGoingReport().observe(viewLifecycleOwner) { report ->
                report?.let {
                    viewModel.setReport(it)
                    if (it.isFinish) fixJustUIEnded()
                    viewModel.getChatHistory().removeObservers(viewLifecycleOwner)
                    viewModel.getChatHistory().observe(viewLifecycleOwner) { listChat ->
                        Log.d("ChatbotFragment", listChat.joinToString())
                        if (listChat.isNotEmpty()) showRecyclerList(listChat)
                    }
                    showLoading(false)
                } ?: run {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.addReport {
                            lifecycleScope.launch(Dispatchers.Main) {
                                viewModel.getChatHistory().removeObservers(viewLifecycleOwner)
                                viewModel.getChatHistory().observe(viewLifecycleOwner) { listChat ->
                                    Log.d("ChatbotFragment", listChat.joinToString())
                                    if (listChat.isNotEmpty()) showRecyclerList(listChat)
                                }
                                showLoading(false)
                            }
                        }
                    }
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.getBotState()?.let { state ->
                        Log.d("ChatbotFragment", state.remainClassJson)
                        mChatbot.setRemainingIntentClasses(state.remainClassJson)
                        if (state.isEnded) {
                            withContext(Dispatchers.Main) { setToEnded(true) }
                        }
                    }
                }
            }
        }

        binding.btnChooseOption.setOnClickListener {
            dialog?.show()
        }

        mBottomSheetBehavior = BottomSheetBehavior.from(binding.introBotomSheet).apply {
            binding.layoutBottomSheet.apply {
                visibility = View.GONE
                val blackHalfAlpha = Color.parseColor("#7F000000")
                setBackgroundColor(blackHalfAlpha)
            }
            peekHeight = 0
            state = BottomSheetBehavior.STATE_COLLAPSED
            animateBtmSheet(binding.layoutBottomSheet, Anim.FadeIn)
        }

        mBottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    animateBtmSheet(binding.layoutBottomSheet, Anim.FadeOut)
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })

        binding.layoutBottomSheet.setOnClickListener {
            animateBtmSheet(binding.layoutBottomSheet, Anim.FadeOut)
        }

        binding.btnStartChat.setOnClickListener {
            animateBtmSheet(binding.layoutBottomSheet, Anim.FadeOut)
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
            sendUserChat(chatToSend)
        }

        binding.btnEndLaporan.setOnClickListener {
            showDialogAkhiri("Akhiri Laporan?", "Setelah diakhiri, laporan tidak ada bisa ditambah dan akan diproses lebih lanjut.", "Laporan diakhiri!")
        }

        binding.btnCancelLaporan.setOnClickListener {
            showDialogBatalkan("Batalkan Laporan?", "Setelah dibatalkan, laporan akan dihapus.", "Laporan dibatalkan!")
        }

    }

    private fun setInputUI(context: Context, input: Chatbot.Input, inputFormat: InputFormat) {

        /*
        * Parse data
        * */

        val itemsOption = inputFormat.options?.toTypedArray() ?: arrayOf("null")
        val itemsState = BooleanArray(itemsOption.size) { false }
        val radioCheckedItem = -1  /* -1 = tidak memilih apapun */

        when(input) {
            Chatbot.Input.ENDED -> {
                setToEnded(true)
            }
            Chatbot.Input.CHAT -> {
                setToOption(false)
            }
            Chatbot.Input.RADIO_BUTTON -> {

                /*
                * Confirmation dialog: radio button
                * */

                dialog = MaterialAlertDialogBuilder(context)
                    .setTitle(inputFormat.title)
                    .setNeutralButton("Batal") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setPositiveButton("Kirim") { dialog, which ->
                        // Respond to positive button press

                        /* log */
                        // showToast("$which") // -1 ?

                        /* set chat */
                        for (i in itemsState.indices) {
                            if (itemsState[i]) {
                                sendUserChat(itemsOption[i])
                                break
                            }
                        }
                    }
                    // Single-choice items (initialized with checked item)
                    .setSingleChoiceItems(itemsOption, radioCheckedItem) { dialog, which ->
                        // Respond to item chosen

                        /* set selected item to true */
                        Arrays.fill(itemsState, false)
                        itemsState[which] = true

                        /* log */
                        //showToast("${itemsOption[which]} : ${itemsState[which]}")
                        //showToast("$which") // index array -> 0/1/2
                    }

                setToOption(true, inputFormat.title.toString())

            }
            Chatbot.Input.CHECK_BOX -> {

                /*
                * Confirmation dialog: checkbox
                * */

                dialog = MaterialAlertDialogBuilder(context)
                    .setTitle(inputFormat.title)
                    .setNeutralButton("Batal") { dialog, which ->
                        // Respond to neutral button press
                    }
                    .setPositiveButton("Kirim") { dialog, which ->
                        // Respond to positive button press

                        /* log itemsState */

                        //showToast("$which") // -1 ?
                        /*showToast(Arrays.deepToString(arrayOf(itemsState)).apply {
                            replace("true", "1")
                            replace("false", "0")
                        })*/

                        /* build chat respond */

                        var respond = String()
                        for (i in 0..itemsState.size-1) {
                            if (itemsState[i]) {
                                if (respond.isNotEmpty())
                                    respond = concat(respond, ", ${itemsOption[i]}")
                                else respond = concat(respond, itemsOption[i])
                            }
                        }
                        sendUserChat(respond)
                    }
                    //Multi-choice items (initialized with checked items)
                    .setMultiChoiceItems(itemsOption, itemsState) { dialog, which, checked ->
                        // Respond to item chosen

                        /* set selected item to true/false */
                        itemsState[which] = checked

                        /* log: index : [apakah tercheck (true) atau tidak (false)] */
                        //showToast("$which : $checked")
                    }

                setToOption(true, inputFormat.title.toString())

            }
        }

    }

    private fun setToEnded(isEnd: Boolean) {
        if(isEnd) {
            binding.edtChat.visibility = View.GONE
            binding.btnKirim.visibility = View.GONE
            binding.btnChooseOption.visibility = View.GONE
        } else {
            binding.edtChat.visibility = View.VISIBLE
            binding.btnKirim.visibility = View.VISIBLE
        }
    }

    private fun setToOption(isOption: Boolean, title: String = "null") {
        if (isOption) {
            binding.edtChat.visibility = View.GONE
            binding.btnKirim.visibility = View.GONE
            binding.btnChooseOption.text = title
            binding.btnChooseOption.visibility = View.VISIBLE
        } else {
            binding.edtChat.setText(String(), TextView.BufferType.EDITABLE)
            binding.edtChat.visibility = View.VISIBLE
            binding.btnKirim.visibility = View.VISIBLE
            binding.btnChooseOption.text = title
            binding.btnChooseOption.visibility = View.GONE
        }
    }

    private fun sendUserChat(chat: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.sendUserChat(chat)
            withContext(Dispatchers.Main) {
                mChatbot.chat(chat)
            }
        }
    }

    private fun setChatTo(text: String) {
        binding.edtChat.apply {
            isEnabled = false
            setText(text)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                (activity as AppCompatActivity).finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogAkhiri(title: String, desc: String, respond: String) {
        view?.context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(desc)
                .setNegativeButton("Tidak") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Ya") { dialog, which ->
                    // Respond to positive button press
                    setReportEndedUI()
                }
                .show()
        }
    }

    private fun showDialogBatalkan(title: String, desc: String, respond: String) {
        view?.context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(desc)
                .setNegativeButton("Tidak") { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton("Ya") { dialog, which ->
                    // Respond to positive button press
                    showToast(respond)
                }
                .show()
        }
    }

    private fun showRecyclerList(listChat: List<Chat>) {
        binding.rvChat.layoutManager = LinearLayoutManager(view?.context)

        val listChatAdapter = ChatAdapter(listChat)
        binding.rvChat.adapter = listChatAdapter

        listChatAdapter.setOnItemClickCallback(object : ChatAdapter.OnItemClickCallback {
            override fun onItemClicked(chat: Chat) {
                chat.apply { showToast("${this.chat}, $id, $isUser") }
            }
        })

        /* scroll to top recycler view */
        binding.rvChat.adapter?.itemCount?.also { itemCount ->
            binding.rvChat.scrollToPosition(itemCount-1)
        }
    }

    private fun animateBtmSheet(target: CoordinatorLayout, type: Anim) {
        val blackHalfAlpha = Color.parseColor("#7F000000")
        val transparent = Color.TRANSPARENT
        val colorFrom = if (type == Anim.FadeIn) transparent else blackHalfAlpha
        val colorTo = if (type == Anim.FadeIn) blackHalfAlpha else transparent

        ObjectAnimator.ofInt(
            target, "backgroundColor", colorFrom, colorTo
        ).apply {
            duration = 150
            doOnStart {
                if (type == Anim.FadeIn) {
                    binding.layoutBottomSheet.visibility = View.VISIBLE
                } else mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            doOnEnd {
                if (type == Anim.FadeOut) {
                    binding.layoutBottomSheet.visibility = View.GONE
                    mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                } else mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            setEvaluator(ArgbEvaluator())
            repeatMode = ValueAnimator.REVERSE
            repeatCount = 0
            start()
        }
    }

    enum class Anim {
        FadeIn, FadeOut
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutBgLoading.visibility = View.VISIBLE
        } else {
            binding.layoutBgLoading.visibility = View.GONE
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

    fun concat(vararg string: String): String {
        val sb = StringBuilder()
        for (s in string) {
            sb.append(s)
        }

        return sb.toString()
    }

    fun setReportEndedUI() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.setLaporanEnded {
                lifecycleScope.launch(Dispatchers.Main) {
                    fixJustUIEnded()
                }
            }
        }
    }

    fun fixJustUIEnded() {
        setToEnded(true)
        val dateEnded = viewModel.getReportDate()
        binding.tvEndedReport.text = "Laporan berhasil dibuat pada tanggal " + dateEnded
        binding.tvEndedReport.visibility = View.VISIBLE
        binding.btnEndLaporan.visibility = View.GONE
        binding.btnCancelLaporan.visibility = View.GONE
    }

}