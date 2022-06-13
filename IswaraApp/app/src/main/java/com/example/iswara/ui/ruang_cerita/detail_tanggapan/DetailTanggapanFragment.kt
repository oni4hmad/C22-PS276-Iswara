package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.iswara.R
import com.example.iswara.data.network.Cerita
import com.example.iswara.data.network.Tanggapan
import com.example.iswara.data.preferences.Session
import com.example.iswara.data.preferences.SessionPreference
import com.example.iswara.databinding.FragmentDetailTanggapanBinding
import com.example.iswara.utils.dateToString
import com.example.iswara.utils.formatDateString
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.*


class DetailTanggapanFragment : Fragment() {

    private lateinit var binding: FragmentDetailTanggapanBinding
    private lateinit var viewModel: DetailTanggapanViewModel
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var session: Session
    private var flagNewTanggapan = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailTanggapanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SessionPreference(view.context).getSession()?.also {
            session = it
        }

        viewModel = ViewModelProvider(this).get(DetailTanggapanViewModel::class.java)
        viewModel.listTanggapan.observe(viewLifecycleOwner) { listTanggapan ->
            showRecyclerList(listTanggapan)
            if (flagNewTanggapan) {
                flagNewTanggapan = false
                doNewTanggapanAnimation()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { toastText ->
                showToast(toastText)
            }
        }
        viewModel.isSucceed.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { isSucceed ->
                if (isSucceed) {
                    viewModel.getTanggapan(callback = {
                        flagNewTanggapan = true
                    })
                }
            }
        }

        Glide.with(binding.ivUser.context)
            .load("https://loremflickr.com/200/100")
            .into(binding.ivUser)

        mBottomSheetBehavior = BottomSheetBehavior.from(binding.tanggapanBotomSheet).apply {
            binding.layoutBottomSheet.apply {
                visibility = View.GONE
                val blackHalfAlpha = Color.parseColor("#7F000000")
                setBackgroundColor(blackHalfAlpha)
            }
            peekHeight = 0
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.edtTanggapan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(text: Editable?) {
                if (text.toString().isNotEmpty())
                    binding.btnKirim.visibility = View.VISIBLE
                else binding.btnKirim.visibility = View.GONE
            }
        })

        binding.btnKirim.setOnClickListener {

            val tanggapan = binding.edtTanggapan.text.toString()
            val name = session.name ?: ""
            viewModel.postTanggapan(name, dateToString(Date()), tanggapan)
        }

        binding.layoutBottomSheet.setOnClickListener {
            animateBtmSheet(binding.layoutBottomSheet, Anim.FadeOut)
        }

        binding.editTanggapan.setOnClickListener {
            showToast("edit!")
        }

        binding.deleteTanggapan.setOnClickListener {
            showToast("delete!")
        }

        DetailTanggapanFragmentArgs.fromBundle(arguments as Bundle).cerita.also {
            viewModel.setCerita(it)
            viewModel.getTanggapan()
            setCerita(it)
        }

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Detail Cerita"
            setDisplayHomeAsUpEnabled(true)
        }

    }

    private fun doNewTanggapanAnimation() {
        binding.apply {
            binding.edtTanggapan.setText(String())

            btnKirim.isEnabled = false
            edtTanggapan.isEnabled = false
            val textToSend = edtTanggapan.text.toString()
            // viewModel.addTanggapan(textToSend)
            edtTanggapan.setText(String(), TextView.BufferType.EDITABLE)
            btnKirim.isEnabled = true
            edtTanggapan.isEnabled = true

            /* scroll to top nestedScrollView */
            val x = lineCerita.x.toInt()
            val y = lineCerita.y.toInt()
            nsvTanggapan.fling(500)
            nsvTanggapan.smoothScrollTo(x, y)

            /* blink the new tanggapan */
            rvTanggapan.post {
                /* itemCount = lastPostition karena rv dibalik (sementara) */
                rvTanggapan.adapter?.itemCount?.also { itemCount ->
                    rvTanggapan.findViewHolderForAdapterPosition(itemCount-1)?.itemView?.also {
                        animateCard(it as CardView)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.edtTanggapan.isEnabled = false
            binding.layoutBgLoading.visibility = View.VISIBLE
        } else {
            binding.edtTanggapan.isEnabled = true
            binding.layoutBgLoading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tanggapan_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
            R.id.edit_story -> {
                showToast("edit story!")
                return true
            }
            R.id.delete_story -> {
                showToast("delete story!")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerList(listTanggapan: List<Tanggapan>) {
        binding.rvTanggapan.layoutManager = LinearLayoutManager(view?.context)

        /* sementara: soalnya kalau dari API bisa langsung urutannya mulai dari yang paling baru */
        (binding.rvTanggapan.layoutManager as LinearLayoutManager).reverseLayout = true

        val listTanggapanAdapter = ListTanggapanAdapter(listTanggapan)
        binding.rvTanggapan.adapter = listTanggapanAdapter

        listTanggapanAdapter.setOnItemClickCallback(object : ListTanggapanAdapter.OnItemClickCallback {
            override fun onItemClicked(tanggapan: Tanggapan) {
                /* show bottom sheet */
                animateBtmSheet(binding.layoutBottomSheet, Anim.FadeIn)
            }
        })
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

    private fun animateCard(target: CardView) {
        ObjectAnimator.ofInt(
            target, "cardBackgroundColor", Color.WHITE, Color.LTGRAY,
            Color.WHITE
        ).apply {
            duration = 1500
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

    private fun setCerita(cerita: Cerita) {
        binding.tvName.text = cerita.name
        binding.tvDate.text = formatDateString(cerita.date)
        binding.tvCerita.text = cerita.cerita
        binding.tvTanggapan.text = cerita.tanggapanCount.toString()
        binding.tvSupport.text = cerita.tanggapanCount.toString()
    }

}