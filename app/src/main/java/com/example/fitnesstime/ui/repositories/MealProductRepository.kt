package com.example.fitnesstime.ui.repositories

import com.example.fitnesstime.connection.ApiInterface
import com.example.fitnesstime.ui.model.ServiceResponse
import com.example.fitnesstime.ui.model.SingleProductNutritionInformation
import retrofit2.Response
import retrofit2.http.Query

class MealProductRepository (private val api: ApiInterface){

    suspend fun getMeals( meal_type: String, email: String, date: String) = api.getMeals(meal_type, email, date)

    suspend fun getAllProduct() = api.getAllProduct()

    suspend fun getOneProduct(productName: String) = api.getOneProduct(productName)

    suspend fun addProduct(mealType : String, email : String, quantity : Int, productId: Int, date: String) = api.addProduct(mealType, email, quantity, productId, date)

    suspend fun deleteProduct(email: String, date: String, productName: String, mealType: String) = api.deleteProduct(email, date, productName, mealType)
}