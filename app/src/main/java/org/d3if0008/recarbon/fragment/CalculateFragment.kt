package org.d3if0008.recarbon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
            peekHeight = 300
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        return binding.root
    }
}