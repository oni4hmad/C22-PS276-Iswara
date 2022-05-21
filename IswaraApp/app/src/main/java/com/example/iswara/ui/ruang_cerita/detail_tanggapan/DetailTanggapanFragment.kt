package com.example.iswara.ui.ruang_cerita.detail_tanggapan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.iswara.R
import com.example.iswara.databinding.FragmentDetailTanggapanBinding
import com.example.iswara.ui.ruang_cerita.CeritaItem
import com.example.iswara.ui.ruang_cerita.ListCeritaAdapter
import org.w3c.dom.Text

class DetailTanggapanFragment : Fragment() {

    private lateinit var binding: FragmentDetailTanggapanBinding
    private lateinit var viewModel: DetailTanggapanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailTanggapanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTanggapanViewModel::class.java)
        viewModel.listTanggapan.observe(viewLifecycleOwner) { listTanggapan ->
            showRecyclerList(listTanggapan)
        }

        Glide.with(binding.ivUser2.context)
            .load("https://loremflickr.com/200/100")
            .into(binding.ivUser2)

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
    }

    private fun showRecyclerList(listTanggapan: List<TanggapanItem>) {
        binding.rvTanggapan.layoutManager = LinearLayoutManager(view?.context)

        val listTanggapanAdapter = ListTanggapanAdapter(listTanggapan)
        binding.rvTanggapan.adapter = listTanggapanAdapter
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}