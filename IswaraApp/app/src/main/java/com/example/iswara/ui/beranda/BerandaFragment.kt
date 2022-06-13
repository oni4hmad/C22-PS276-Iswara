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
import com.bumptech.glide.Glide
import com.example.iswara.R
import com.example.iswara.data.preferences.SessionPreference
import com.example.iswara.databinding.FragmentBerandaBinding


class BerandaFragment : Fragment() {

    private lateinit var binding : FragmentBerandaBinding
    private lateinit var mSessionPreference: SessionPreference

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

        mSessionPreference = SessionPreference(view.context)
        if (mSessionPreference.getSession() != null) {
            val name = mSessionPreference.getSession()?.name
            Glide.with(binding.ivUserPhoto.context)
                .load("https://loremflickr.com/200/100")
                .into(binding.ivUserPhoto)
            binding.tvUserName.text = getString(R.string.hai_ambar_text_holder, name)
        }

        binding.cvRuangan.setOnClickListener {
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