package com.example.iswara.ui.ruang_cerita.add_cerita

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.iswara.R
import com.example.iswara.databinding.FragmentAddCeritaBinding
import com.example.iswara.ui.ruang_cerita.cerita_tablayout.TabCeritaActivity
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


        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Cerita"
        }
        
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}