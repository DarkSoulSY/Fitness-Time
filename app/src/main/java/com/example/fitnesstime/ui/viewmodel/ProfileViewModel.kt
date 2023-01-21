package com.example.fitnesstime.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    val api = RetrofitInstance.retrofit

    private val userAccountInformationRepository = UserAccountInformationRepository(api)

    val profileFirstName = MutableLiveData<String>()

    val profileLastName = MutableLiveData<String>()

    val profileEmail = MutableLiveData<String>()

    @OptIn(DelicateCoroutinesApi::class)
    fun setProfile(email: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = userAccountInformationRepository.getProfileInfo(email)
            GlobalScope.launch(Dispatchers.Main) {
                if (response.isSuccessful) {
                    profileFirstName.value = response.body()!!.Data!!.first_name
                    profileLastName.value = response.body()!!.Data!!.last_name
                    profileEmail.value = response.body()!!.Data!!.email
                }
            }
        }

    }


}