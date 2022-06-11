package com.example.iswara.ui.settings_laporanku

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSettingsLaporankuBinding


class SettingsLaporankuFragment : Fragment() {

    private lateinit var binding : FragmentSettingsLaporankuBinding
    private lateinit var viewModel: SettingsLaporankuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsLaporankuBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsLaporankuViewModel::class.java]

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Laporanku"
        }

        viewModel.listLaporan.observe(viewLifecycleOwner) { listLaporan ->
            showRecyclerList(listLaporan)
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

    private fun showRecyclerList(listLaporan: List<LaporanItem>) {
        binding.rvAllLaporanku.layoutManager = LinearLayoutManager(view?.context)

        val listLaporanAdapter = LaporanAdapter(listLaporan)
        binding.rvAllLaporanku.adapter = listLaporanAdapter

        listLaporanAdapter.setOnItemClickCallback(object : LaporanAdapter.OnItemClickCallback {
            override fun onItemClicked(laporan: LaporanItem) {
                showToast("${laporan.idLaporan}, ${laporan.date}")
            }
        })
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}