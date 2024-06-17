package com.example.prettypet.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.prettypet.fragments.ClinicFragment
import com.example.prettypet.fragments.EventFragment
import com.example.prettypet.fragments.HomeFragment
import com.example.prettypet.R

import com.example.prettypet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            replaceFragment(HomeFragment())
            binding.bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId){
                    R.id.navigation_home -> replaceFragment(HomeFragment())
                    R.id.navigation_manual -> replaceFragment(EventFragment())
                    R.id.navigation_clinic -> replaceFragment(ClinicFragment())
                }
                true
            }


    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}