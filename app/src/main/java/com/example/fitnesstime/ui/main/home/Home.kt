package com.example.fitnesstime.ui.main.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}