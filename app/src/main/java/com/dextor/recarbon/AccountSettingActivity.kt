package com.dextor.recarbon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dextor.recarbon.databinding.ActivityAccountSettingBinding

class AccountSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackFromAkun.setOnClickListener{
            onBackPressed()
        }
    }
}