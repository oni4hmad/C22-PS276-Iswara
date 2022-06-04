package com.example.iswara.ui.beranda

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.iswara.R
import com.example.iswara.databinding.FragmentBerandaBinding


class BerandaFragment : Fragment() {

    private lateinit var binding : FragmentBerandaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBerandaBinding.inflate(inflater,  container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setShowHideAnimationEnabled(false)
            hide()
        }

        binding.cvRuangan.setOnClickListener {
            Toast.makeText(activity,"Ruangan will available soon \uD83E\uDD70", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_berandaFragment2_to_tabCeritaActivity)
        }

        binding.cvKonsultasi.setOnClickListener {
            Toast.makeText(activity,"Thanks for click this.Konsultasi will available soon \uD83D\uDE4F", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_berandaFragment2_to_konsultasiSoonFragment)
        }

        binding.cvDarurat.setOnClickListener {
            Toast.makeText(activity,"\uD83D\uDE4B\uD83C\uDFFB\u200D♀️Excuse me, Darurat Feature will available soon", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_berandaFragment2_to_daruratFragment)
        }


    }

}