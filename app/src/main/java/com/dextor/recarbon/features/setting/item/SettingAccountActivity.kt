package com.dextor.recarbon.features.setting.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dextor.recarbon.databinding.ActivitySettingAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SettingAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingAccountBinding
    lateinit var auth: FirebaseAuth
//    private var databaseReference: DatabaseReference? = null
//    private var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
//        databaseReference = database?.reference!!.child("users")

        loadProfile()

        binding.btnBackFromAkun.setOnClickListener{
            onBackPressed()
        }
    }

    private fun loadProfile() {

        val currentUser = auth.currentUser
//        val userreference = databaseReference?.child(user?.uid!!)

//        userreference?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val name = snapshot.child("username").value.toString()
//                Log.d("SettingsFragment", "Username: $name")
//                binding.tvUsernameAkun.text = snapshot.child("username").value.toString()
//                binding.tvEmailAkun.text = snapshot.child("email").value.toString()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
        binding.tvUsernameAkun.text = currentUser?.displayName
        binding.tvEmailAkun.text = currentUser?.email
    }
}