package com.example.fitnesstime.ui.model

data class CreateAccountPreferencesDTO(
    val email: String,
    val weight_plan_type: String,
    val weight_goal: Float,
    val weekly_activity: String,
    val caloric_plan_goal: Int
)
