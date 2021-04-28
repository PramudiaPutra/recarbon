package com.dextor.recarbon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dextor.recarbon.databinding.ActivityAccountSettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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