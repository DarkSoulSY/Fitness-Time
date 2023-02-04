package com.example.fitnesstime.ui.model

import com.google.gson.annotations.SerializedName


data class Diary(
    @SerializedName("current_weight")
    var currentWeight: String?,
    @SerializedName("date")
    var date : String
)