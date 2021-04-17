package com.dextor.recarbon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dextor.recarbon.R
import com.dextor.recarbon.databinding.ActivityMainBinding
import com.dextor.recarbon.fragment.ArticleFragment
import com.dextor.recarbon.fragment.CalculateFragment
import com.dextor.recarbon.fragment.HomeFragment
import com.dextor.recarbon.fragment.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val home = HomeFragment()
        val calculate = CalculateFragment()
        val article = ArticleFragment()
        val setting = SettingsFragment()


        binding.bottomNavigation.selectedItemId = R.id.ic_kalkulator
        setFragment(calculate)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> setFragment(home)
                R.id.ic_kalkulator -> setFragment(calculate)
                R.id.ic_artikel ->setFragment(article)
                R.id.ic_settings -> setFragment(setting)
            }
            true
        }

    }

    private fun setFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment,fragment)
        commit()
    }

}