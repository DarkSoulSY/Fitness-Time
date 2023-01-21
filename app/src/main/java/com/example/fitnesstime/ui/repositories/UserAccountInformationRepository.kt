package com.example.fitnesstime.ui.repositories

import com.example.fitnesstime.connection.ApiInterface
import com.example.fitnesstime.ui.model.CreateAccountDTO
import com.example.fitnesstime.ui.model.CreateAccountPreferencesDTO

class UserAccountInformationRepository(private val api: ApiInterface) {


    suspend fun createAccount(createAccountDTO: CreateAccountDTO) = api.createAccount(createAccountDTO)

    suspend fun addPreferences(createAccountPreferencesDTO: CreateAccountPreferencesDTO) = api.addPreferences(createAccountPreferencesDTO)

    suspend fun signIn(email: String, password: String) = api.signIn(email, password)

    suspend fun getBaseGoal(email: String) = api.getBaseGoal(email)

    suspend fun getBodyMassIndexInfo(email:String) = api.getBodyMassIndexInfo(email)

    suspend fun getProfileInfo(email: String) = api.getProfileInfo(email)

}