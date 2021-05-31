package com.dextor.recarbon.features.calculator.categories

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.constant.CALCULATOR_MENU
import com.dextor.recarbon.constant.MENU_NAVIGATION
import com.dextor.recarbon.features.calculator.CalculateHistoryAdapter
import com.dextor.recarbon.databinding.FragmentMobilBinding
import com.dextor.recarbon.model.HistoryData
import com.dextor.recarbon.features.calculator.CalculateFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MobilFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var binding: FragmentMobilBinding
    private lateinit var list: ArrayList<HistoryData>
    private lateinit var calculateHistoryAdapter: CalculateHistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMobilBinding.inflate(layoutInflater, container, false)

//        databaseReference = database?.reference!!.child("carbon")

        list = ArrayList()
        calculateHistoryAdapter = CalculateHistoryAdapter(list)
//        val calculateFragment = CalculateFragment()

        binding.btnSimpanDetail.setOnClickListener {
            hitungKarbonMobil()
            activity?.let {
                val intent = Intent(it, MainActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra(MENU_NAVIGATION, CALCULATOR_MENU)
                }
                it.startActivity(intent)
            }

        }
        return binding.root
    }

    private fun hitungKarbonMobil() {

        //mengambil uid
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val currentUserId = currentUser?.uid


        //mengambil value tiap data history
        val uid = currentUserId.toString()
        val tanggal = ubahTanggal()
        val jarak = binding.edJarak.text.toString()
        val deskripsi = binding.edDeskripsi.text.toString()
        val time = ubahJam()
        val karbon = jarak.toDouble() * 0.27

        //menyimpan data history kedalam model
        val history = HistoryData(
            tanggal,
            R.drawable.car_black_icon,
            "Mobil",
            time,
            deskripsi,
            karbon.toString()
        )

        //menyimpan data history ke database
        database = FirebaseDatabase.getInstance().reference
        database.child("history").child(uid).child(database.push().key.toString()).setValue(history)


        calculateHistoryAdapter.notifyDataSetChanged()

    }

    private fun ubahTanggal(): String {
        val dateNow = Calendar.getInstance()
        dateNow.add(Calendar.DATE, 0)
        val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+07:00'", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        return format1.format(dateNow.time)
    }

    private fun ubahJam(): String {
        val dateNow = Calendar.getInstance()
        dateNow.add(Calendar.DATE, 1)
        val format1 = SimpleDateFormat("HH:mm", Locale.getDefault())
        println(dateNow.time)

        val formatted = format1.format(dateNow.time)
        println(formatted)
        return formatted
    }


}