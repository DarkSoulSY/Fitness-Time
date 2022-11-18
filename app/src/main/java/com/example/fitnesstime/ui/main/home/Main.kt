package com.example.fitnesstime.ui.main.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityMainBinding
import com.example.fitnesstime.ui.main.fragments.*

class Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){

                R.id.home -> replaceFragment(HomeFragment())
                R.id.progress -> replaceFragment(ProgressFragment())
                R.id.diary -> replaceFragment(DiaryFragment())
                R.id.bmi -> replaceFragment(BodyMassIndexFragment())
                R.id.settings -> replaceFragment(SettingsFragment())

            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment).commit()




    }

}