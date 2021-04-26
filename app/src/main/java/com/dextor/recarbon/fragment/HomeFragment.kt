package com.dextor.recarbon.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dextor.recarbon.AddSosmedActivity
import com.dextor.recarbon.adapter.SosmedAdapter
import com.dextor.recarbon.databinding.FragmentHomeBinding
import com.dextor.recarbon.dummy.SosmedDummy

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sosmedAdapter: SosmedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        initRecycler()
        binding.btnAddPosting.setOnClickListener {
            val intent = Intent(context, AddSosmedActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun initRecycler() {
        val getSosmed = SosmedDummy.dataSet()
        binding.rvSosmedList.apply {
            layoutManager = LinearLayoutManager(context)
            sosmedAdapter = SosmedAdapter(context, getSosmed)
            adapter = sosmedAdapter
        }
    }
}