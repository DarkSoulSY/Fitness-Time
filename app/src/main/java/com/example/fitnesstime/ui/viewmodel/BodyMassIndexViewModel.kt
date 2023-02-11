package com.example.fitnesstime.ui.viewmodel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.enum.Gender
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BodyMassIndexViewModel : ViewModel() {

    private val api = RetrofitInstance.retrofit

    val firstTime = MutableLiveData<Boolean>()

    private val userAccountInformationRepository: UserAccountInformationRepository = UserAccountInformationRepository(api)

    private val _height = MutableLiveData<Float>(0f)
    val height: LiveData<Float> = _height

    private val _weight = MutableLiveData<Float>(0f)
    val weight: LiveData<Float> = _weight

    private val _gender = MutableLiveData<Gender>()
    val gender: LiveData<Gender> = _gender





    fun setBodyMassIndexInformation(context: Context, email: String){
        GlobalScope.launch(Dispatchers.IO) {
            val response = userAccountInformationRepository.getBodyMassIndexInfo(email)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _height.value = response.body()!!.Data!!.height.toFloat()
                    _weight.value = response.body()!!.Data!!.weight.toFloat()
                    _gender.value = Gender.valueOf(response.body()!!.Data!!.gender)
                    Toast.makeText(context, getBMIResult(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun calculateBMI(): Float {
        val heightInMeters = height.value!! / 100
        return weight.value!! / (heightInMeters * heightInMeters)
    }

    fun getBMIResult(): String {
        val bmi = calculateBMI()
        return when (_gender.value) {
            Gender.Male -> {
                when {
                    bmi < 20 -> "Underweight"
                    bmi in 20.0..24.9 -> "Normal weight"
                    bmi in 25.0..29.9 -> "Overweight"
                    else -> "Obesity"
                }
            }
            Gender.Female -> {
                when {
                    bmi < 19 -> "Underweight"
                    bmi in 19.0..24.9 -> "Normal weight"
                    bmi in 25.0..29.9 -> "Overweight"
                    else -> "Obesity"
                }
            }
            else -> {
                return "0"
            }
        }
    }
}
