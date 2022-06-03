package com.example.iswara.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var settingFragBinding: FragmentSettingsBinding

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingFragBinding = FragmentSettingsBinding.inflate(inflater,container,false)
        return settingFragBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        (activity as AppCompatActivity)?.supportActionBar?.apply {
            title = "Pengaturan"
            show()

        }

        settingFragBinding.changeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_profileSettingActivity)
        }

        settingFragBinding.changePassword.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_ubahPasswordActivity)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }


}