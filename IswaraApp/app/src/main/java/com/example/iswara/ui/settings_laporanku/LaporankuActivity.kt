package com.example.iswara.ui.settings_laporanku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iswara.databinding.ActivityLaporankuBinding

class LaporankuActivity : AppCompatActivity() {

    private lateinit var laporankuSettingBinding : ActivityLaporankuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        laporankuSettingBinding = ActivityLaporankuBinding.inflate(layoutInflater)

        setContentView(laporankuSettingBinding.root)

        supportActionBar?.title = "Laporanku"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}