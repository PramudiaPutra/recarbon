package com.dextor.recarbon.features.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dextor.recarbon.databinding.FragmentCalculateBinding
import com.dextor.recarbon.model.SosmedData
import com.dextor.recarbon.databinding.FragmentSosmedBinding
import com.dextor.recarbon.model.HistoryData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

//import com.dextor.recarbon.dummy.SosmedDummy

class SosmedFragment : Fragment() {

    companion object {
        val list: ArrayList<SosmedData> = ArrayList<SosmedData>()
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var binding: FragmentSosmedBinding
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var sosmedItem: ArrayList<SosmedData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSosmedBinding.inflate(layoutInflater, container, false)

        sosmedItem = ArrayList()
        sosmedItem.clear()

        auth = Firebase.auth
        val currentUser = auth.currentUser
        val currentUserId = currentUser?.uid
        val uid = currentUserId.toString()

        database = FirebaseDatabase.getInstance().reference.child("stories")
        getStory(database)

        binding.btnAddPosting.setOnClickListener {
            val intent = Intent(context, SosmedAddActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun getStory(database: DatabaseReference) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //mengambil data dan menampung ke model HistoryData.kt
                for (snapshot in dataSnapshot.children) {
                    val stories = snapshot.getValue(SosmedData::class.java)
                    sosmedItem.add(stories!!)
                }

                with(binding.rvSosmedList) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = SosmedAdapter(sosmedItem)
                    setHasFixedSize(true)
                    adapter?.notifyDataSetChanged()
                }

            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}