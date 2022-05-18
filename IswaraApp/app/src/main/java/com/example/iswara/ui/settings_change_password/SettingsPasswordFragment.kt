package com.example.iswara.ui.settings_change_password

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iswara.R

class SettingsPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsPasswordFragment()
    }

    private lateinit var viewModel: SettingsPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}