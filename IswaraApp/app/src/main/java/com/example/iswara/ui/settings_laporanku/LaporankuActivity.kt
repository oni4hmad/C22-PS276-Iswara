package com.example.iswara.ui.settings_laporanku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.iswara.R
import com.example.iswara.databinding.ActivityLaporankuBinding

class LaporankuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLaporankuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporankuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findNavController(R.id.nav_host_laporanku).setGraph(R.navigation.list_laporan_nav, intent.extras)
    }

}