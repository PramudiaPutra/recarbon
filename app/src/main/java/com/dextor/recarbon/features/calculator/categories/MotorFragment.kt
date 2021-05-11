package com.dextor.recarbon.features.calculator.categories

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dextor.recarbon.MainActivity
import com.dextor.recarbon.R
import com.dextor.recarbon.features.calculator.CalculateHistoryAdapter
import com.dextor.recarbon.databinding.FragmentMotorBinding
import com.dextor.recarbon.model.HistoryData
import com.dextor.recarbon.features.calculator.CalculateFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MotorFragment : Fragment() {

    private lateinit var binding: FragmentMotorBinding
    private lateinit var list: ArrayList<HistoryData>
    private lateinit var calculateHistoryAdapter: CalculateHistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMotorBinding.inflate(layoutInflater, container, false)

        list = ArrayList()
        calculateHistoryAdapter = CalculateHistoryAdapter(list)
        val calculateFragment = CalculateFragment()

        binding.btnSimpanDetail.setOnClickListener {
            hitungKarbonMotor()
            activity?.let {
                val intent = Intent(it, MainActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                it.startActivity(intent)
            }

        }
        return binding.root
    }

    private fun hitungKarbonMotor() {

        val tanggal = ubahTanggal()
        val jarak = binding.edJarak.text.toString()
        val deskripsi = binding.edDeskripsi.text.toString()
        val time = ubahJam()
        val karbon = jarak.toDouble() * 0.15

        CalculateFragment.list.add(HistoryData(tanggal, R.drawable.motor_black_icon, "Motor", time, deskripsi, karbon.toString()))
        calculateHistoryAdapter.notifyDataSetChanged()

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