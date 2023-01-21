package com.example.fitnesstime.ui.model

data class ServiceResponse<T>(
    val Data: T?,
    val Message: String? = null,
    val Success: Boolean = false
)