package com.example.fitnesstime.ui.model

import com.google.gson.annotations.SerializedName

data class EmptyServiceResponse(
    @SerializedName("Message")
    var Message: String? = null,
    @SerializedName("Success")
    var Success: Boolean = false
)