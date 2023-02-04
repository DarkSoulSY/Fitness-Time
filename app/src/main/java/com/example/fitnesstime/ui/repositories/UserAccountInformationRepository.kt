package com.example.fitnesstime.ui.repositories

import com.example.fitnesstime.connection.ApiInterface
import com.example.fitnesstime.ui.model.CreateAccountNPreferences

class UserAccountInformationRepository(private val api: ApiInterface) {

    suspend fun createAccountNPreferences(createAccountNPreferences: CreateAccountNPreferences) = api.createAccountNPreferences(createAccountNPreferences)

    suspend fun signIn(email: String, password: String) = api.signIn(email, password)

    suspend fun getBaseGoal(email: String) = api.getBaseGoal(email)

    suspend fun getBodyMassIndexInfo(email:String) = api.getBodyMassIndexInfo(email)

    suspend fun getProfileInfo(email: String) = api.getProfileInfo(email)

    suspend fun getMacros(email: String) = api.getMacros(email)

    suspend fun editProfile(email: String, password: String, firstName: String?, lastName: String?) = api.editProfile(email, password, firstName, lastName)

    suspend fun changePassword(email: String, oldPassword: String, newPassword: String) = api.changePassword(email, oldPassword, newPassword)

}