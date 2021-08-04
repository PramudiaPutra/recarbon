package com.dextor.recarbon.features.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.dextor.recarbon.model.HistoryData
import com.dextor.recarbon.databinding.FragmentCalculateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt


class CalculateFragment : Fragment() {

    companion object{
        val list: ArrayList<HistoryData> =  ArrayList<HistoryData>()
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentCalculateBinding
    private lateinit var historyItem: ArrayList<HistoryData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculateBinding.inflate(layoutInflater, container, false)
        historyItem = ArrayList()
        historyItem.clear()

        //mengambil uid
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val currentUserId = currentUser?.uid
        val uid = currentUserId.toString()

        database = FirebaseDatabase.getInstance().reference.child("history").child(uid)
        getDataCarbon(database)

        //bottom sheet
        BottomSheetBehavior.from(binding.flBottomSheet).apply {
            this.peekHeight = 650
            this.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        //pindah ke halaman kalkulator
        binding.btnAddCarbon.setOnClickListener {
            val intent = Intent(context, CalculateAddActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    //mengambil data dari firebase
    private fun getDataCarbon(database: DatabaseReference) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //mengambil data dan menampung ke model HistoryData.kt
                for (snapshot in dataSnapshot.children) {
                    val history = snapshot.getValue(HistoryData::class.java)
                    historyItem.add(history!!)
                }

                //membuat recyclerview
                with(binding.rvDateHistory) {
                    adapter = CalculateHistoryAdapter(historyItem)
                    setHasFixedSize(true)
                    adapter?.notifyDataSetChanged()

                    //menjumlah total karbon
                    if (historyItem.isEmpty()) {
                        binding.jumlahKarbon.text = "0"
                    } else {
                        val jumlah = historyItem.sumByDouble {
                            it.carbon!!.toDouble()
                        }
                        binding.jumlahKarbon.text = "${BigDecimal(jumlah).setScale(2, RoundingMode.HALF_EVEN)}"
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        )

    }
}