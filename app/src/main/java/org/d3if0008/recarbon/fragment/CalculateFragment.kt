package org.d3if0008.recarbon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.d3if0008.recarbon.R
import org.d3if0008.recarbon.adapter.HistoryAdapter
import org.d3if0008.recarbon.data.HistoryData
import org.d3if0008.recarbon.databinding.FragmentCalculateBinding


class CalculateFragment : Fragment() {

    private lateinit var binding: FragmentCalculateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculateBinding.inflate(layoutInflater, container, false)

        BottomSheetBehavior.from(binding.flBottomSheet).apply {
            this.setPeekHeight(650)
            this.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
        with(binding.rvDateHistory){
            adapter = HistoryAdapter(getData())
            setHasFixedSize(true)
        }
        return binding.root

    }

    private fun getData(): List<HistoryData>{
        return listOf(
            HistoryData("2021-04-17T09:41:00+07:00",R.drawable.motor_black_icon,"Mobil","14.00","Ini Mobil","8Kg"),
            HistoryData("2021-04-17T10:41:00+07:00",R.drawable.motor_black_icon,"Mobil","14.00","Ini Mobil","8Kg"),
            HistoryData("2021-04-16T14:41:00+07:00",R.drawable.motor_black_icon,"Mobil","14.00","Ini Mobil","8Kg"),
            HistoryData("2021-04-15T20:41:00+07:00",R.drawable.motor_black_icon,"Mobil","14.00","Ini Mobil","8Kg"),
            HistoryData("2021-04-15T11:41:00+07:00",R.drawable.motor_black_icon,"Mobil","14.00","Ini Mobil","8Kg")

        )
    }
}