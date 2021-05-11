package com.dextor.recarbon.features.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dextor.recarbon.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.dextor.recarbon.model.HistoryData
import com.dextor.recarbon.databinding.FragmentCalculateBinding


class CalculateFragment : Fragment() {

    companion object{
        val list: ArrayList<HistoryData> =  ArrayList<HistoryData>()
    }

    private lateinit var binding: FragmentCalculateBinding
    private lateinit var historyItem: ArrayList<HistoryData>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculateBinding.inflate(layoutInflater, container, false)

        historyItem = ArrayList<HistoryData>()

        BottomSheetBehavior.from(binding.flBottomSheet).apply {
            this.peekHeight = 650
            this.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        if (list.isEmpty()){
            binding.jumlahKarbon.text = "0"
        }else{

            val jumlah = list.sumByDouble {
                Log.d("Ini Karbon", it.carbon )
                it.carbon.toDouble()
            }
            binding.jumlahKarbon.text = "$jumlah"
        }

        for (s in list){
            historyItem.add(s)
            with(binding.rvDateHistory){
                adapter?.notifyDataSetChanged()
            }

        }

        with(binding.rvDateHistory) {
            adapter = CalculateHistoryAdapter(list)
            setHasFixedSize(true)
        }

        binding.btnAddCarbon.setOnClickListener {
            val intent = Intent(context, CalculateAddActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun getData(): ArrayList<HistoryData> {
        return arrayListOf(

            HistoryData(
                "2021-04-17T10:41:00+07:00",
                R.drawable.motor_black_icon,
                "Motor",
                "10.00",
                "Ini Mobil",
                "1Kg"
            ),
            HistoryData(
                "2021-04-17T10:41:00+07:00",
                R.drawable.motor_black_icon,
                "Motor",
                "10.00",
                "Ini Mobil",
                "1Kg"
            ),
            HistoryData(
                "2021-04-17T10:41:00+07:00",
                R.drawable.motor_black_icon,
                "Motor",
                "10.00",
                "Ini Mobil",
                "1Kg"
            ),
            HistoryData(
                "2021-04-17T10:41:00+07:00",
                R.drawable.motor_black_icon,
                "Motor",
                "10.00",
                "Ini Mobil",
                "1Kg"
            )

        )
    }


}