package com.example.fitnesstime.ui.model

import com.google.gson.annotations.SerializedName

data class SingleProductNutritionInformation(
    @SerializedName("Total_Carbs")
    val carbohydrates: String,
    @SerializedName("Total_Fat")
    val totalFat: String,
    @SerializedName("added_suger")
    val addedSugar: String,
    @SerializedName("calcium")
    val calcium: String,
    @SerializedName("calories")
    val calories: String,
    @SerializedName("cholesterol")
    val cholesterol: String,
    @SerializedName("fiber")
    val fiber: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("iron")
    val iron: String,
    @SerializedName("monounsaturated")
    val monounsaturated: String,
    @SerializedName("polyunsaturated")
    val polyunsaturated: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("protein")
    val protein: String,
    @SerializedName("saturated")
    val saturated: String,
    @SerializedName("sodium")
    val sodium: String,
    @SerializedName("suger")
    val sugar: String,
    @SerializedName("suger_alcohols")
    val sugarAlcohols: String,
    @SerializedName("trans")
    val trans: String,
    @SerializedName("vitamin_a")
    val vitamin_a: String,
    @SerializedName("vitamin_c")
    val vitamin_c: String,
    @SerializedName("vitamin_d")
    val vitamin_d: String

)