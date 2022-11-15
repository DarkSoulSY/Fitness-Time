package com.example.fitnesstime.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityFinishSignUpBinding

class FinishSignUp : AppCompatActivity() {

    private lateinit var binding: ActivityFinishSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}