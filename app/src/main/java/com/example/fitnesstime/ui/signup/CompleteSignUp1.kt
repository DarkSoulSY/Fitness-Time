package com.example.fitnesstime.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityCompleteSignUp1Binding

class CompleteSignUp1 : AppCompatActivity() {

    private lateinit var binding: ActivityCompleteSignUp1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteSignUp1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}