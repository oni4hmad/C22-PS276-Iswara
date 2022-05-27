package com.example.iswara.ui.settings_change_password

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSettingsPasswordBinding

class SettingsPasswordFragment : Fragment() {

    private lateinit var binding: FragmentSettingsPasswordBinding
    private lateinit var viewModel: SettingsPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsPasswordViewModel::class.java]


        binding.textinputOldPassword.apply {
            setValidation(this.editText)
        }
        binding.textinputPassword.apply {
            setValidation(this.editText)
        }
        binding.textinputRepeatPassword.apply {
            setValidation(this.editText)
        }
    }

    private fun tryChangePassword() {
        val passwordOld = binding.edtPassword.text.toString()
        val passwordNew = binding.edtPassword.text.toString()
        val passwordNewRepeat = binding.edtRepeatPassword.text.toString()
        if (passwordNew != passwordNewRepeat) { binding.textinputRepeatPassword.error = "Passwords do not match."}
        val isPasswordOldInvalid = binding.textinputOldPassword.isError || passwordOld.isEmpty()
        val isPasswordNewInvalid = binding.textinputPassword.isError || passwordNew.isEmpty()
        val isPasswordNewRepeatInvalid = binding.textinputPassword.isError || binding.textinputRepeatPassword.isError || passwordNew.isEmpty()
        when {
            isPasswordOldInvalid -> binding.edtOldPassword.requestFocus()
            isPasswordNewInvalid -> binding.edtPassword.requestFocus()
            isPasswordNewRepeatInvalid -> binding.edtRepeatPassword.requestFocus()
            else -> showToast("change passowrd!")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}