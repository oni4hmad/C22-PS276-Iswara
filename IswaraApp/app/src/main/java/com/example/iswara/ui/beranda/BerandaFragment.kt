package com.example.iswara.ui.beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.iswara.R
import com.example.iswara.databinding.FragmentBerandaBinding


class BerandaFragment : Fragment() {

    private var _berandaBinding : FragmentBerandaBinding? = null
    private val berandaBinding get() = _berandaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //activity?.actionBar?.hide()
        super.onCreate(savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //activity?.actionBar?.hide()
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _berandaBinding = FragmentBerandaBinding.bind(view)

        //activity?.actionBar?.hide()
        //removing bg and shadow things of bottom nav menu
        berandaBinding?.bottomNavViewMenu?.background = null
        berandaBinding?.bottomNavViewMenu?.menu?.getItem(1)?.isEnabled =false

        berandaBinding?.cvRuangan?.setOnClickListener {
            Toast.makeText(activity,"Ruangan will available soon \uD83E\uDD70", Toast.LENGTH_SHORT).show()
        }

        berandaBinding?.cvKonsultasi?.setOnClickListener {
            Toast.makeText(activity,"Thanks for click this.Konsultasi will available soon \uD83D\uDE4F", Toast.LENGTH_SHORT).show()
        }

        berandaBinding?.cvDarurat?.setOnClickListener {
            Toast.makeText(activity,"\uD83D\uDE4B\uD83C\uDFFB\u200D♀️Excuse me, Darurat Feature will available soon", Toast.LENGTH_SHORT).show()
        }

        berandaBinding?.fabChatbotAra?.setOnClickListener {
            Toast.makeText(activity,"Opening chat bot Ara ❤️ ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _berandaBinding = null
    }

}