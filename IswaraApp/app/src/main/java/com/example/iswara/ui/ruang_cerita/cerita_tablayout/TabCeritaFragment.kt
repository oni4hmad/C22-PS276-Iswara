package com.example.iswara.ui.ruang_cerita.cerita_tablayout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.iswara.R
import com.example.iswara.databinding.FragmentTabCeritaBinding
import com.example.iswara.ui.beranda.BerandaActivity
import com.example.iswara.ui.login.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabCeritaFragment : Fragment() {

    private lateinit var binding: FragmentTabCeritaBinding
    private lateinit var viewModel: TabCeritaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabCeritaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TabCeritaViewModel::class.java)

        /* tab layout: section pager adapter, view pager */
        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity() as TabCeritaActivity)
        val viewPager: ViewPager2 = binding.vpCerita
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabsCerita
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        (requireActivity() as TabCeritaActivity).supportActionBar?.elevation = 0f

        /* fab: add cerita */
        binding.fabAddCerita.setOnClickListener {
            showToast("add cerita!")
            findNavController().navigate(R.id.action_tabCeritaFragment2_to_addCeritaFragment2)
        }

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)

            title = "Ruang Cerita"
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(view?.context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                (activity as AppCompatActivity).finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_cerita_1,
            R.string.tab_cerita_2
        )
    }



}