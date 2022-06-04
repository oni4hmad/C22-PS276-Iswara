package com.example.iswara.ui.chatbot

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iswara.databinding.FragmentChatbotBinding
import com.example.iswara.ui.ruang_cerita.detail_tanggapan.DetailTanggapanFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class ChatbotFragment : Fragment() {

    private lateinit var binding: FragmentChatbotBinding
    private lateinit var viewModel: ChatbotViewModel
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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
            viewModel.sendChat(chatToSend)
        }

        binding.btnEndLaporan.setOnClickListener {

            // showDialog("Akhiri Laporan?", "Setelah diakhiri, laporan tidak ada bisa ditambah dan akan diproses lebih lanjut.", "Laporan diakhiri!")

            /*
            * Simple dialog
            * */

            /*val items = arrayOf("Item 1", "Item 2", "Item 3")

            MaterialAlertDialogBuilder(view?.context)
                .setTitle("Tap untuk pilih!")
                .setItems(items) { dialog, which ->
                    // Respond to item chosen
                    showToast(which.toString()) // index array -> 0/1/2
                }
                .show()*/

            /*
            * Confirmation dialog: radio button
            * */

            /*val singleItems = arrayOf("Item 1", "Item 2", "Item 3")
            val checkedItem = 0 *//* -1 = tidak memilih apapun *//*

            MaterialAlertDialogBuilder(view?.context)
                .setTitle("Pilih salah satu!")
                .setNeutralButton("Batal") { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("OK") { dialog, which ->
                    // Respond to positive button press
                    showToast("$which") // -1 ?
                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    // Respond to item chosen
                    showToast("$which") // index array -> 0/1/2
                }
                .show()*/

            /*
            * Confirmation dialog: checkbox
            * */

            val multiItems = arrayOf("Item 1", "Item 2", "Item 3")
            val checkedItems = booleanArrayOf(true, false, false, false)

            var respond: String = ""

            MaterialAlertDialogBuilder(view?.context)
                .setTitle("Pilih beberapa!")
                .setNeutralButton("Cancel") { dialog, which ->
                    // Respond to neutral button press
                }
                .setNegativeButton("Tidak") { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("OK") { dialog, which ->
                    // Respond to positive button press
                    showToast(Arrays.deepToString(arrayOf(checkedItems)).apply {
                        replace("true", "1")
                        replace("false", "0")
                    }) /* log checkedItems */
                    for (i in 0..checkedItems.size-1) {
                        if (checkedItems[i]) {
                            if (respond.isNotEmpty())
                                respond = concat(respond, ", ${multiItems[i]}")
                            else respond = concat(respond, "${multiItems[i]}")
                        }
                    }
                    viewModel.sendChat(respond)
                    showToast("$which") // -1 ?
                }
                //Multi-choice items (initialized with checked items)
                .setMultiChoiceItems(multiItems, checkedItems) { dialog, which, checked ->
                    // Respond to item chosen
                    checkedItems[which] = checked
                    showToast("$which : $checked") // index : [apakah tercheck (true) atau tidak (false)]
                }
                .show()

        }

        binding.btnCancelLaporan.setOnClickListener {
            showDialog("Batalkan Laporan?", "Setelah dibatalkan, laporan akan dihapus.", "Laporan dibatalkan!")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                //findNavController().navigate(R.id.action_addCeritaFragment2_to_tabCeritaFragment2)
                //findNavController().navigateUp()
                (activity as AppCompatActivity).finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog(title: String, desc: String, respond: String) {
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

}