package com.example.iswara.ui.settings_profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iswara.R

class SettingsProfileFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsProfileFragment()
    }

    private lateinit var viewModel: SettingsProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}