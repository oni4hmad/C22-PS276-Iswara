package com.example.iswara.ui.ruang_cerita.cerita_user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iswara.R
import com.example.iswara.data.network.Cerita
import com.example.iswara.data.preferences.Session
import com.example.iswara.data.preferences.SessionPreference
import com.example.iswara.databinding.FragmentUserCeritaBinding
import com.example.iswara.ui.ruang_cerita.ListCeritaAdapter
import com.example.iswara.ui.ruang_cerita.cerita_tablayout.TabCeritaFragmentArgs

class UserCeritaFragment : Fragment() {

    private lateinit var binding: FragmentUserCeritaBinding
    private lateinit var viewModel: UserCeritaViewModel
    private lateinit var session: Session

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserCeritaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SessionPreference(view.context).getSession()?.also {
            session = it
        }

        viewModel = ViewModelProvider(this, UserCeritaViewModelFactory(session))[UserCeritaViewModel::class.java]
        viewModel.listCerita.observe(viewLifecycleOwner) { listCerita ->
            showRecyclerList(listCerita)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            when {
                error.isError && error.type == UserCeritaViewModel.ErrorType.NO_DATA ->
                    showError(true, "Tidak ada data")
                error.isError ->
                    error.errorMsg?.let { showError(true, it) }
                else -> showError(false)
            }
        }

        arguments?.let {
            TabCeritaFragmentArgs.fromBundle(it).msg?.let { msg ->
                showToast(msg)
            }
        }

        binding.srlCerita.setOnRefreshListener {
            loadStory()
        }
    }

    private fun loadStory() {
        viewModel.getUserStory(1, 10)
    }

    private fun showRecyclerList(listCerita: List<Cerita>) {
        binding.rvUserCerita.layoutManager = LinearLayoutManager(view?.context)

        val listCeritaAdapter = ListCeritaAdapter(listCerita)
        binding.rvUserCerita.adapter = listCeritaAdapter

        listCeritaAdapter.setOnItemClickCallback(object : ListCeritaAdapter.OnItemClickCallback {
            override fun onItemClicked(cerita: Cerita) {
                findNavController().navigate(R.id.action_tabCeritaFragment2_to_detailTanggapanFragment, bundleOf("cerita" to cerita), null)
            }
        })
    }

    private fun showError(isError: Boolean, msg: String = "") {
        if (isError) showToast(msg)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.srlCerita.isRefreshing = true
            binding.rvUserCerita.visibility = View.GONE
        } else {
            binding.srlCerita.isRefreshing = false
            binding.rvUserCerita.visibility = View.VISIBLE
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}