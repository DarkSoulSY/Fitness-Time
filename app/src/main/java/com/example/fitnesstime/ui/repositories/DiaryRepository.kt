package com.example.fitnesstime.ui.repositories

import com.example.fitnesstime.connection.ApiInterface
import com.example.fitnesstime.ui.fragments.AddConsumedCalories
import com.example.fitnesstime.ui.model.AddDailyWeight
import com.example.fitnesstime.ui.model.ServiceResponse
import retrofit2.Response
import retrofit2.http.Body


class DiaryRepository(private val api: ApiInterface) {


    suspend fun getHistory(email:String) = api.getHistory(email)

    suspend fun addDailyWeight(addDailyWeight: AddDailyWeight) = api.addDailyWeight(addDailyWeight)

    suspend fun addConsumedCalories(addConsumedCalories: AddConsumedCalories) = api.addConsumedCalories(addConsumedCalories)



}