package com.dextor.recarbon.features.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dextor.recarbon.adapter.SosmedAdapter
import com.dextor.recarbon.model.SosmedData
import com.dextor.recarbon.databinding.FragmentHomeBinding
//import com.dextor.recarbon.dummy.SosmedDummy

class HomeFragment : Fragment() {

    companion object{
        val list: ArrayList<SosmedData> =  ArrayList<SosmedData>()

    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sosmedAdapter: SosmedAdapter
    private lateinit var sosmedItem: ArrayList<SosmedData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        sosmedItem = ArrayList()
        for (s in list){
            sosmedItem.add(s)
            with(binding.rvSosmedList){
                adapter?.notifyDataSetChanged()
            }
        }

       initRecycler()

        binding.btnAddPosting.setOnClickListener {
            val intent = Intent(context, AddSosmedActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun initRecycler() {

        binding.rvSosmedList.apply {
            layoutManager = LinearLayoutManager(context)
            sosmedAdapter = SosmedAdapter(list)
            adapter = sosmedAdapter
        }
    }

}