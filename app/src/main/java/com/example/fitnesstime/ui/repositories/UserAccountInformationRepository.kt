package com.example.fitnesstime.ui.repositories

import com.example.fitnesstime.connection.ApiInterface
import com.example.fitnesstime.ui.model.CreateAccountDTO
import com.example.fitnesstime.ui.model.CreateAccountPreferencesDTO

class UserAccountInformationRepository(private val api: ApiInterface) {


    suspend fun createAccountPost(createAccountDTO: CreateAccountDTO) = api.createAccount(createAccountDTO)

    suspend fun createAccountPreferences(createAccountPreferencesDTO: CreateAccountPreferencesDTO) = api.addPreferences(createAccountPreferencesDTO)

    suspend fun getBaseGoal(email: String) = api.getBaseGoal(email)

    suspend fun signUserIn(email: String, password: String) = api.signInAccount(email, password)



       /* GlobalScope.launch(Dispatchers.IO) {
            val response = api.createAccount(createAccountDTO)
            if (response.isSuccessful)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, response.body()!!.Message.toString(), Toast.LENGTH_LONG).show()
                    success = response.body()!!.Success
                    Log.v("repo success create2", response.body()!!.Message.toString() + " " + response.body()!!.Success.toString())
                }
            else
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, response.body()!!.Message.toString(), Toast.LENGTH_LONG).show()
                    success = response.body()!!.Success
                    Log.v("repo success create3", response.body()!!.Message.toString() + " " + response.body()!!.Success.toString())
                }
        }*/




    /*{
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.addPreferences(createAccountPreferencesDTO)
            if (response.isSuccessful)
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, response.body()!!.Message.toString(), Toast.LENGTH_LONG).show()
                    success = response.body()!!.Success
                    Log.v("repo success pref2", response.body()!!.Message.toString() + " " + response.body()!!.Success.toString())
                }
            else
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, response.body()!!.Message.toString(), Toast.LENGTH_LONG).show()
                    success = response.body()!!.Success
                    Log.v("repo success pref3", response.body()!!.Message.toString() + " " + response.body()!!.Success.toString())
                }
        }
    }*/


}