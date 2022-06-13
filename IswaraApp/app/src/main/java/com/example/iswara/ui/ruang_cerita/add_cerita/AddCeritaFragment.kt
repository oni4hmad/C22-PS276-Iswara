package com.example.iswara.ui.ruang_cerita.add_cerita

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.iswara.R
import com.example.iswara.data.preferences.Session
import com.example.iswara.data.preferences.SessionPreference
import com.example.iswara.databinding.FragmentAddCeritaBinding
import com.example.iswara.utils.dateToString
import java.text.SimpleDateFormat
import java.util.*

class AddCeritaFragment : Fragment() {

    private lateinit var binding: FragmentAddCeritaBinding
    private lateinit var viewModel: AddCeritaViewModel
    private lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCeritaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SessionPreference(view.context).getSession()?.also {
            session = it
        }

        viewModel = ViewModelProvider(this)[AddCeritaViewModel::class.java]
        viewModel.isSucceed.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { isSucceed ->
                if (isSucceed) toStoryList("Story added successfully")
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { toastText ->
                showToast(toastText)
            }
        }

        Glide.with(binding.ivUser3.context)
            .load("https://loremflickr.com/200/100")
            .into(binding.ivUser3)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Tambah Cerita"
        }
        
        setHasOptionsMenu(true)

    }

    private fun toStoryList(msg: String) {
        val toListStoryFragment = AddCeritaFragmentDirections.actionAddCeritaFragment2ToTabCeritaFragment2(msg)
        view?.findNavController()?.navigate(toListStoryFragment)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            (activity as AppCompatActivity).supportActionBar?.hide()
            binding.layoutBgLoading.visibility = View.VISIBLE
        } else {
            (activity as AppCompatActivity).supportActionBar?.show()
            binding.layoutBgLoading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_cerita_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
//        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_story -> {
                val ceritaToPost = binding.edtCerita.text.toString()
                if (ceritaToPost.isNotEmpty()) {
                    viewModel.postStory(session, dateToString(Date()), ceritaToPost)
                } else {
                    binding.edtCerita.error = "Couldn't be empty"
                }
                return true
            }
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}