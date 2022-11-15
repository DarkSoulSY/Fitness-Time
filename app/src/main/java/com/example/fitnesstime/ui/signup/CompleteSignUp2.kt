package com.example.fitnesstime.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityCompleteSignUp2Binding

class CompleteSignUp2 : AppCompatActivity() {

    private lateinit var binding:ActivityCompleteSignUp2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteSignUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}