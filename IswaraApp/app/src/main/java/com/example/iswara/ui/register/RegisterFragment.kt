package com.example.iswara.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.iswara.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.textinputEmail.apply {
            setValidation(this.editText)
        }
        binding.textinputPassword.apply {
            setValidation(this.editText)
        }
        binding.textinputRepeatPassword.apply {
            setValidation(this.editText)
        }
        binding.btnRegister.setOnClickListener {
            tryRegister()
        }
    }

    private fun tryRegister() {
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val passwordRepeat = binding.edtRepeatPassword.text.toString()
        if (password != passwordRepeat) { binding.textinputRepeatPassword.error = "Passwords do not match."}
        val isNameInvalid = name.isEmpty()
        val isEmailInvalid = binding.textinputEmail.isError || email.isEmpty()
        val isPasswordInvalid = binding.textinputPassword.isError || password.isEmpty()
        val isPasswordRepeatInvalid = binding.textinputPassword.isError || binding.textinputRepeatPassword.isError || password.isEmpty()
        when {
            isNameInvalid -> binding.edtName.requestFocus()
            isEmailInvalid -> binding.edtEmail.requestFocus()
            isPasswordInvalid -> binding.edtPassword.requestFocus()
            isPasswordRepeatInvalid -> binding.edtRepeatPassword.requestFocus()
            else -> showToast("register!")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}