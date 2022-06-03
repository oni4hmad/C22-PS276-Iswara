package com.example.iswara.ui.settings_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSettingsProfileBinding

class ProfileSettingActivity : AppCompatActivity() {
    
    private lateinit var profileSettingBinding: FragmentSettingsProfileBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileSettingBinding = FragmentSettingsProfileBinding.inflate(layoutInflater)
        
        setContentView(profileSettingBinding.root)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu,menu)

        val menuItem = menu?.findItem(R.id.custom_save_button)
        menuItem?.actionView?.setOnClickListener {
            menu.performIdentifierAction(menuItem.getItemId(), 0)
        }

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.custom_save_button -> Toast.makeText(this,"Saving y",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

}