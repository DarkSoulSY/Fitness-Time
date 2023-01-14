package com.example.fitnesstime.ui.model

data class ServiceResponse<T>(
    val data: T?,
    val Message: String? = null,
    val Success: Boolean = false
)