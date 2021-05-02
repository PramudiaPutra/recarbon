package com.dextor.recarbon.features.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dextor.recarbon.R
import com.dextor.recarbon.model.CategoryData
import com.dextor.recarbon.databinding.ActivityAddCalculateBinding
import com.dextor.recarbon.dummy.CategoryDummy
import com.dextor.recarbon.features.calculator.categories.CategoryAdapter
import com.dextor.recarbon.features.calculator.categories.MobilFragment
import com.dextor.recarbon.features.calculator.categories.MotorFragment

class AddCalculateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCalculateBinding
    private lateinit var categoryAdapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mobilDetailFragment = MobilFragment()
        setFragment(mobilDetailFragment)
        initRecycler()
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initRecycler() {
        val getCategory = CategoryDummy.dataSet()

        binding.rvKategori.apply {

            layoutManager = LinearLayoutManager(this@AddCalculateActivity)
            layoutManager = LinearLayoutManager(
                this@AddCalculateActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            categoryAdapter = CategoryAdapter(this@AddCalculateActivity, getCategory)
            adapter = categoryAdapter
        }

        categoryAdapter.setOnItemClickCallback(object : CategoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: CategoryData) {
                val mobilDetailFragment = MobilFragment()
                val motorDetailFragment = MotorFragment()
                when (data.categoryTitle) {
                    "Mobil" -> setFragment(mobilDetailFragment)
                    "Motor" -> setFragment(motorDetailFragment)
                }
            }
        })
    }

    private fun setFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.layout_detail_categori, fragment)
        commit()
    }
}