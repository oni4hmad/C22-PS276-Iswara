package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iswara.databinding.FragmentDetailTanggapanBinding
import com.example.iswara.ui.ruang_cerita.CeritaItem
import com.example.iswara.ui.ruang_cerita.ListCeritaAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior


class DetailTanggapanFragment : Fragment() {

    private lateinit var binding: FragmentDetailTanggapanBinding
    private lateinit var viewModel: DetailTanggapanViewModel
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailTanggapanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBottomSheetBehavior = BottomSheetBehavior.from(binding.tanggapanBotomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = 0
        }

        viewModel = ViewModelProvider(this).get(DetailTanggapanViewModel::class.java)
        viewModel.listTanggapan.observe(viewLifecycleOwner) { listTanggapan ->
            showRecyclerList(listTanggapan)
        }

        Glide.with(binding.ivUser.context)
            .load("https://loremflickr.com/200/100")
            .into(binding.ivUser)

        binding.edtTanggapan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                if (text.toString().length > 0)
                    binding.btnKirim.visibility = View.VISIBLE
                else binding.btnKirim.visibility = View.GONE
            }
        })

        binding.btnKirim.setOnClickListener {
            binding.btnKirim.isEnabled = false
            binding.edtTanggapan.isEnabled = false
            val textToSend = binding.edtTanggapan.text.toString()
            viewModel.addTanggapan(textToSend)
            binding.edtTanggapan.setText(String(), TextView.BufferType.EDITABLE)
            binding.btnKirim.isEnabled = true
            binding.edtTanggapan.isEnabled = true

            /* scroll to top nestedScrollView */
            val x = binding.lineCerita.x.toInt()
            val y = binding.lineCerita.y.toInt()
            binding.nsvTanggapan.fling(500)
            binding.nsvTanggapan.smoothScrollTo(x, y)

            /* blink the new tanggapan */
            binding.rvTanggapan.post {
                /* itemCount = lastPostition karena rv dibalik (sementara) */
                binding.rvTanggapan.adapter?.itemCount?.also { itemCount ->
                    binding.rvTanggapan.findViewHolderForAdapterPosition(itemCount-1)?.itemView?.also {
                        animateCard(it as CardView)
                    }
                }
            }
        }
    }

    private fun showRecyclerList(listTanggapan: List<TanggapanItem>) {
        binding.rvTanggapan.layoutManager = LinearLayoutManager(view?.context)

        /* sementara: soalnya kalau dari API bisa langsung urutannya mulai dari yang paling baru */
        (binding.rvTanggapan.layoutManager as LinearLayoutManager).reverseLayout = true

        val listTanggapanAdapter = ListTanggapanAdapter(listTanggapan)
        binding.rvTanggapan.adapter = listTanggapanAdapter

        listTanggapanAdapter.setOnItemClickCallback(object : ListTanggapanAdapter.OnItemClickCallback {
            override fun onItemClicked(tanggapan: TanggapanItem) {
                tanggapan.apply { showToast("$idTanggapan, $name, $date, ${this.tanggapan}") }

                /* show bottom sheet */
                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
    }

    private fun animateCard(target: CardView) {
        val anim = ObjectAnimator.ofInt(
            target, "cardBackgroundColor", Color.WHITE, Color.LTGRAY,
            Color.WHITE
        )
        anim.duration = 1500
        anim.setEvaluator(ArgbEvaluator())
        anim.repeatMode = ValueAnimator.REVERSE
        anim.repeatCount = 0
        anim.start()
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}