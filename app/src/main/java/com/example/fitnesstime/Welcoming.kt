package com.example.fitnesstime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitnesstime.databinding.ActivityWelcomingBinding
import com.example.fitnesstime.ui.signin.SignIn
import com.example.fitnesstime.ui.signup.SignUp

class Welcoming : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnsignup.setOnClickListener {
            var intent: Intent = Intent(this, SignUp()::class.java)
            startActivity(intent)
        }
        binding.btnsignin.setOnClickListener {
            var intent: Intent = Intent(this, SignIn()::class.java)
            startActivity(intent)
        }
    }


}