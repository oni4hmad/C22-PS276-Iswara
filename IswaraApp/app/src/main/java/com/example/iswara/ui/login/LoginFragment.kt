package com.example.iswara.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.iswara.R
import com.example.iswara.databinding.FragmentLoginBinding
import com.example.iswara.ui.beranda.BerandaActivity

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

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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
            else -> {
                showToast("login!")
                goToBerandaActivity()
            }


        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

    private fun goToBerandaActivity() {
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_berandaActivity)
        activity?.finish()


    }

}