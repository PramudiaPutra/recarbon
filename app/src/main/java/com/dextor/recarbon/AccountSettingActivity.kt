package com.dextor.recarbon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dextor.recarbon.R
import com.dextor.recarbon.databinding.ActivityAccountSettingBinding
import com.dextor.recarbon.fragment.SettingsFragment

class AccountSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackFromAkun.setOnClickListener{
            val intent = Intent(this, SettingsFragment::class.java)
            startActivity(intent)
        }
    }
}