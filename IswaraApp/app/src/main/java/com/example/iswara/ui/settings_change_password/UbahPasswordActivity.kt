package com.example.iswara.ui.settings_change_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.iswara.R
import com.example.iswara.databinding.ActivityUbahPasswordBinding

class UbahPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUbahPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu,menu)

        val menuItem = menu?.findItem(R.id.custom_save_button)
        menuItem?.actionView?.setOnClickListener {
            menu.performIdentifierAction(menuItem.itemId, 0)
        }

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.custom_save_button -> {
                Toast.makeText(this,"Saving password", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}