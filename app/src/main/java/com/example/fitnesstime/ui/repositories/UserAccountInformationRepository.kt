package com.example.fitnesstime.ui.repositories

import com.example.fitnesstime.connection.ApiInterface
import com.example.fitnesstime.ui.model.CreateAccountNPreferences

class UserAccountInformationRepository(private val api: ApiInterface) {

    suspend fun createAccountNPreferences(createAccountNPreferences: CreateAccountNPreferences) = api.createAccountNPreferences(createAccountNPreferences)

    suspend fun signIn(email: String, password: String) = api.signIn(email, password)

    suspend fun getBaseGoal(email: String) = api.getBaseGoal(email)

    suspend fun getBodyMassIndexInfo(email:String) = api.getBodyMassIndexInfo(email)

    suspend fun getProfileInfo(email: String) = api.getProfileInfo(email)

}