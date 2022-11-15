package com.example.fitnesstime.ui.main.progress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityProgressBinding

class Progress : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}