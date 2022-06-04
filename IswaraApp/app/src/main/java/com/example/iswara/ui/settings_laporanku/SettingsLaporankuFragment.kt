package com.example.iswara.ui.settings_laporanku

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSettingsLaporankuBinding


class SettingsLaporankuFragment : Fragment() {

    private lateinit var laporankuBinding : FragmentSettingsLaporankuBinding
    private lateinit var laporankuViewModel: SettingsLaporankuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        laporankuBinding = FragmentSettingsLaporankuBinding.inflate(inflater,container,false)
        return laporankuBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        laporankuViewModel = ViewModelProvider(this).get(SettingsLaporankuViewModel::class.java)

    }

}