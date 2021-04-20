package com.dextor.recarbon.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dextor.recarbon.AccountSettingActivity
import com.dextor.recarbon.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSettingsBinding.inflate(layoutInflater,container,false)

        binding.conAkun.setOnClickListener{
            val intent = Intent(context, AccountSettingActivity::class.java)
            startActivity(intent)

        }

        return binding.root


    }
}