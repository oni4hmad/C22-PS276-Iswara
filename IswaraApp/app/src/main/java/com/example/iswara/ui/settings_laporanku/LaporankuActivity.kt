package com.example.iswara.ui.settings_laporanku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.iswara.databinding.ActivityLaporankuBinding

class LaporankuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLaporankuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporankuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}