package com.example.fitnesstime.ui.model

data class CreateAccountNPreferences(val first_name: String,
                                     val last_name: String,
                                     val email: String,
                                     val password: String,
                                     val gender: String,
                                     val height: Float,
                                     val weight: Float,
                                     val birthday: String,
                                     val phone: String,
                                     val weight_plan_type: String,
                                     val weight_goal: Float,
                                     val weekly_activity: String,
                                     val caloric_plan_goal: Int)
