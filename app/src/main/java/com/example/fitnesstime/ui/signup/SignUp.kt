package com.example.fitnesstime.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}