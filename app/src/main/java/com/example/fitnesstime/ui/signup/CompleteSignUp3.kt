package com.example.fitnesstime.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityCompleteSignUp3Binding

class CompleteSignUp3 : AppCompatActivity() {

    private lateinit var binding: ActivityCompleteSignUp3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteSignUp3Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}