package com.example.fitnesstime.ui.model

data class CreateAccountDTO(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val gender: String,
    val height: Float,
    val weight: Float,
    val birthday: String,
    val phone: String
    )
