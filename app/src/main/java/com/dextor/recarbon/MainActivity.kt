package com.dextor.recarbon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dextor.recarbon.constant.CALCULATOR_MENU
import com.dextor.recarbon.constant.MENU_NAVIGATION
import com.dextor.recarbon.databinding.ActivityMainBinding
import com.dextor.recarbon.features.article.ArticleFragment
import com.dextor.recarbon.features.calculator.CalculateFragment
import com.dextor.recarbon.features.home.SosmedFragment
import com.dextor.recarbon.features.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuPossition = intent.getStringExtra(MENU_NAVIGATION)

        val home = SosmedFragment()
        val calculate = CalculateFragment()
        val article = ArticleFragment()
        val setting = SettingFragment()

        binding.bottomNavigation.selectedItemId = R.id.ic_home

        setFragment(home)

        if (menuPossition == CALCULATOR_MENU) {
            setFragment(calculate)
            binding.bottomNavigation.selectedItemId = R.id.ic_kalkulator
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> setFragment(home)
                R.id.ic_kalkulator -> setFragment(calculate)
                R.id.ic_artikel -> setFragment(article)
                R.id.ic_settings -> setFragment(setting)
            }
            true
        }

    }

    private fun setFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragment)
        commit()
    }

}