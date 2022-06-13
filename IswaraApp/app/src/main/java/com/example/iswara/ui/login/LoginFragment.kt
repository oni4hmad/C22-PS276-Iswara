package com.example.iswara.ui.login

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
import com.example.iswara.data.preferences.Session
import com.example.iswara.data.preferences.SessionPreference
import com.example.iswara.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var mSessionPreference: SessionPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSessionPreference = SessionPreference(view.context)
        if (mSessionPreference.getSession() != null) {
            goToBerandaActivity()
        }

        LoginFragmentArgs.fromBundle(arguments as Bundle).email?.let {
            binding.edtEmail.setText(it)
        }
        LoginFragmentArgs.fromBundle(arguments as Bundle).password?.let {
            binding.edtPassword.setText(it)
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.session.observe(viewLifecycleOwner) {
            login(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { toastText ->
                showToast(toastText)
            }
        }

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

    private fun login(session: Session) {
        mSessionPreference.setSession(session)
        goToBerandaActivity()
    }

    private fun tryLogin() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val isEmailInvalid = binding.textinputEmail.isError || email.isEmpty()
        val isPasswordInvalid = binding.textinputPassword.isError || password.isEmpty()
        when {
            isEmailInvalid -> binding.edtEmail.requestFocus()
            isPasswordInvalid -> binding.edtPassword.requestFocus()
            else -> viewModel.login(email)
        }
    }

    private fun goToBerandaActivity() {
        view?.findNavController()?.navigate(R.id.action_loginFragment_to_berandaActivity)
        activity?.finish()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutBgLoading.visibility = View.VISIBLE
        } else {
            binding.layoutBgLoading.visibility = View.GONE
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}