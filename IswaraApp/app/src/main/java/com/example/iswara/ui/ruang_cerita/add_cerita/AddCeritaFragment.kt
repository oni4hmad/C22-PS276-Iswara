package com.example.iswara.ui.ruang_cerita.add_cerita

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.iswara.databinding.FragmentAddCeritaBinding
import com.example.iswara.ui.ruang_cerita.detail_tanggapan.DetailTanggapanViewModel

class AddCeritaFragment : Fragment() {

    private lateinit var binding: FragmentAddCeritaBinding
    private lateinit var viewModel: AddCeritaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCeritaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddCeritaViewModel::class.java)

        Glide.with(binding.ivUser3.context)
            .load("https://loremflickr.com/200/100")
            .into(binding.ivUser3)
    }

}