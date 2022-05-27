package com.example.iswara.ui.ruang_cerita.cerita_user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.iswara.R
import com.example.iswara.databinding.FragmentUserCeritaBinding
import com.example.iswara.ui.ruang_cerita.CeritaItem
import com.example.iswara.ui.ruang_cerita.ListCeritaAdapter

class UserCeritaFragment : Fragment() {

    private lateinit var binding: FragmentUserCeritaBinding
    private lateinit var viewModel: UserCeritaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserCeritaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserCeritaViewModel::class.java)
        viewModel.listCerita.observe(viewLifecycleOwner) { listCerita ->
            showRecyclerList(listCerita)
        }
    }

    private fun showRecyclerList(listCerita: List<CeritaItem>) {
        binding.rvUserCerita.layoutManager = LinearLayoutManager(view?.context)

        val listCeritaAdapter = ListCeritaAdapter(listCerita)
        binding.rvUserCerita.adapter = listCeritaAdapter

        listCeritaAdapter.setOnItemClickCallback(object : ListCeritaAdapter.OnItemClickCallback {
            override fun onItemClicked(cerita: CeritaItem) {
                cerita.apply { showToast("$idCerita, $name, $date, $cerita, $tanggapanCount, $supportCount") }
            }
        })
    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

}