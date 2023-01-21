package com.example.fitnesstime.ui.repositories

import com.example.fitnesstime.connection.ApiInterface


class DiaryRepository(private val api: ApiInterface) {


    suspend fun getHistory(email:String) = api.getHistory(email)

}