package com.example.iswara.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.iswara.R
import com.example.iswara.data.preferences.SessionPreference
import com.example.iswara.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    private lateinit var mSessionPreference: SessionPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Pengaturan"
            show()
        }

        binding.changeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_profileSettingActivity)
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_ubahPasswordActivity)
        }

        binding.showReport.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_laporankuActivity)
        }

        binding.logout.setOnClickListener {
            SessionPreference(view.context).clearSession()
            findNavController().navigate(R.id.action_settingsFragment_to_mainActivity)
            (activity as AppCompatActivity).finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}