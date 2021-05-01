package com.dextor.recarbon.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dextor.recarbon.AccountSettingActivity
import com.dextor.recarbon.SignInActivity
import com.dextor.recarbon.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    lateinit var auth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var database: FirebaseDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
//        databaseReference = database?.reference!!.child("users")

        loadProfile()

        binding.conAkun.setOnClickListener {
            val intent = Intent(context, AccountSettingActivity::class.java)
            startActivity(intent)

        }

        return binding.root


    }

    private fun loadProfile() {
        
        val currentUser = auth.currentUser
//        val userreference = databaseReference?.child(user?.uid!!)

//        userreference?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val name = snapshot.child("username").value.toString()
//                Log.d("SettingsFragment", "Username: $name")
//                binding.usernameLogin.text = snapshot.child("username").value.toString()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })

        if (currentUser != null){
            binding.usernameLogin.text = currentUser.displayName
        }
        binding.conLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(context, SignInActivity::class.java))
        }
    }
}