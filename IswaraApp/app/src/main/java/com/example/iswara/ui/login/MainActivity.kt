package com.example.iswara.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.iswara.R
import com.example.iswara.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* buat cek nampilin fragment di main activity */
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = SettingsFragment()
        val fragment = mFragmentManager.findFragmentByTag(SettingsFragment::class.java.simpleName)

        if (fragment !is SettingsFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, SettingsFragment::class.java.simpleName)
            }
        }
    }
}