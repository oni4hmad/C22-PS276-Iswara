package com.example.iswara.ui.beranda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.iswara.R
import com.example.iswara.databinding.ActivityBerandaBinding

class BerandaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val theNavController = findNavController(R.id.fragmentContainerView2)
        binding.bottomNavViewMenu.setupWithNavController(theNavController)

        binding.fabChatbotAra.setOnClickListener {
            theNavController.navigate(R.id.action_berandaFragment2_to_chatbotActivity)
        }

    }

}