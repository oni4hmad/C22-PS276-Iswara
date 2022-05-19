package com.example.iswara.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.iswara.R
import com.example.iswara.ui.beranda.BerandaFragment
import com.example.iswara.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* buat cek nampilin fragment di main activity */
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = BerandaFragment()
        val fragment = mFragmentManager.findFragmentByTag(BerandaFragment::class.java.simpleName)

        if (fragment !is BerandaFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, BerandaFragment::class.java.simpleName)
            }
        }
    }
}