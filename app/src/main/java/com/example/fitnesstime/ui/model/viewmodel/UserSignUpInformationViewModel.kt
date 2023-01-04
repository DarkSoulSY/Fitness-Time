package com.example.fitnesstime.ui.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class UserSignUpInformationViewModel : ViewModel() {

    private val _firstName = MutableLiveData<String>("")
    val firstName: LiveData<String> = _firstName

    private val _lastName = MutableLiveData<String>("")
    val lastName: LiveData<String> = _lastName

    private val _email = MutableLiveData<String>("")
    val email: LiveData<String> = _email

    private val _phoneNumber = MutableLiveData<String> ("")
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _password = MutableLiveData<String>("")
    val password: LiveData<String> = _password

    private val _goal = MutableLiveData<String>("Lose Weight")
    val goal : LiveData<String> = _goal

    private val _gender = MutableLiveData<String>("Male")
    val gender: LiveData<String> = _gender

    private val _age = MutableLiveData<Int>()
    val age: LiveData<Int> = _age

    private val _height = MutableLiveData<Float>()
    val height : LiveData<Float> = _height

    private val _ratio = MutableLiveData<Float>(0.2f)
    val ratio : LiveData<Float> = _ratio

    private val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> = _weight

    private val _goalWeight = MutableLiveData<Float>()
    val goalWeight : LiveData<Float> = _goalWeight

    private val _weeklyActivity = MutableLiveData<String>()
    val weeklyActivity : LiveData<String> = _weeklyActivity


    fun setFirstName(name: String){
        _firstName.value = name
    }

    fun setLastName(name: String){
        _lastName.value = name
    }

    fun setEmail(email: String){
        _email.value = email
    }

    fun setPhoneNumber(phoneNumber: String){
        _phoneNumber.value = phoneNumber
    }

    fun setPassword(password: String){
        _password.value = password
    }

    fun setGoal(goal: String){
        _goal.value = goal
    }

    fun setGender(gender: String){
        _gender.value = gender
    }

    fun setAge(age: Int){
        _age.value = age
    }

    fun setHeight(height: Float){
        _height.value = height
    }

    fun setRatio (ratio: Float){
        _ratio.value = ratio
    }
    fun setWeight(name: Float){
        _weight.value = name
    }

    fun setGoalWeight(goal: Float){
        _goalWeight.value = goal
    }

    fun setWeeklyActivity(activity: String){
        _weeklyActivity.value = activity
    }

    fun convertFloatToString(F: Float): String
    {
        return F.toString()
    }

}