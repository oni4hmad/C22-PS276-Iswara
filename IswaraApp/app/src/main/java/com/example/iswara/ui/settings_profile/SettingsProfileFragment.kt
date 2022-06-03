package com.example.iswara.ui.settings_profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSettingsProfileBinding

class SettingsProfileFragment : Fragment() {

    private lateinit var binding: FragmentSettingsProfileBinding
    private lateinit var viewModel: SettingsProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsProfileViewModel::class.java]

        binding.textinputEmail.apply {
            setValidation(this.editText)
        }
        
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Ubah Profile"
        }



    }

    private fun tryChangeProfile() {
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val isNameInvalid = name.isEmpty()
        val isEmailInvalid = binding.textinputEmail.isError || email.isEmpty()
        when {
            isNameInvalid -> binding.edtName.requestFocus()
            isEmailInvalid -> binding.edtEmail.requestFocus()
            else -> showToast("change profile!")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                (activity as AppCompatActivity).finish()
                //findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}