package com.example.iswara.ui.beranda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.iswara.R
import com.example.iswara.databinding.ActivityBerandaBinding
import com.example.iswara.ui.settings.SettingsFragment

class BerandaActivity : AppCompatActivity() {

    private lateinit var berandaActivityBinding: ActivityBerandaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        berandaActivityBinding = ActivityBerandaBinding.inflate(layoutInflater)

        setContentView(berandaActivityBinding.root)


        berandaActivityBinding.bottomNavViewMenu.background = null
        berandaActivityBinding.bottomNavViewMenu.menu.getItem(1).isEnabled = false





        val theNavController = findNavController(R.id.fragmentContainerView2)
        berandaActivityBinding.bottomNavViewMenu.setupWithNavController(theNavController)

        berandaActivityBinding.fabChatbotAra.setOnClickListener {
            Toast.makeText(this,"Chatbot here", Toast.LENGTH_SHORT).show()
            theNavController.navigate(R.id.action_berandaFragment2_to_chatbotActivity)

        }


    }



}