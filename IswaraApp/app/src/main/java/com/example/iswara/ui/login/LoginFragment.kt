package com.example.iswara.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.iswara.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.textinputEmail.apply {
            setValidation(this.editText)
        }
        binding.textinputPassword.apply {
            setValidation(this.editText)
        }
        binding.btnLogin.setOnClickListener {
            tryLogin()
        }
    }

    private fun tryLogin() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val isEmailInvalid = binding.textinputEmail.isError || email.isEmpty()
        val isPasswordInvalid = binding.textinputPassword.isError || password.isEmpty()
        when {
            isEmailInvalid -> binding.edtEmail.requestFocus()
            isPasswordInvalid -> binding.edtPassword.requestFocus()
            else -> showToast("login!")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}