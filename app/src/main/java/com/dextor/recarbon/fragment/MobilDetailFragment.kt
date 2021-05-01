package com.dextor.recarbon.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.dextor.recarbon.AddCalculateActivity
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.adapter.HistoryAdapter
import com.dextor.recarbon.data.HistoryData
import java.time.LocalDateTime
import com.dextor.recarbon.databinding.FragmentMobilDetailBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MobilDetailFragment : Fragment() {

    private lateinit var binding: FragmentMobilDetailBinding
    private lateinit var list: ArrayList<HistoryData>
    private lateinit var historyAdapter: HistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMobilDetailBinding.inflate(layoutInflater, container, false)

        list = ArrayList()
        historyAdapter = HistoryAdapter(list)
        val calculateFragment = CalculateFragment()

        binding.btnSimpanDetail.setOnClickListener {
            hitungKarbonMobil()
            activity?.let {
                val intent = Intent(it, MainActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                it.startActivity(intent)
            }

//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.calculateActivity, calculateFragment)
//            transaction?.disallowAddToBackStack()
//            transaction?.commit()

        }
        return binding.root
    }

    private fun hitungKarbonMobil() {

        val tanggal = ubahTanggal()
        val jarak = binding.edJarak.text.toString()
        val deskripsi = binding.edDeskripsi.text.toString()
        val time = ubahJam()
        val karbon = jarak.toDouble() * 0.27

        CalculateFragment.list.add(HistoryData(tanggal, R.drawable.car_black_icon, "Mobil", time, deskripsi, karbon.toString()))
        historyAdapter.notifyDataSetChanged()

    }

    private fun ubahTanggal(): String{
        val dateNow = Calendar.getInstance()
        dateNow.add(Calendar.DATE,0)
        val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+07:00'", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        val formatted = format1.format(dateNow.time)
        return formatted
    }

    private fun ubahJam(): String{
        val dateNow = Calendar.getInstance()
        dateNow.add(Calendar.DATE,1)
        val format1 = SimpleDateFormat("HH:mm", Locale.getDefault())
        println(dateNow.time)

        val formatted = format1.format(dateNow.time)
        println(formatted)
        return formatted
    }


}