package com.example.fitnesstime.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnesstime.ui.model.ProductInformation

class DashboardViewModel {

    private val _baseGoal = MutableLiveData<Int>()
    val baseGoal: LiveData<Int> = _baseGoal

    private val _consumedCalories = MutableLiveData<Int>()
    val consumedCalories: LiveData<Int> = _consumedCalories

    private val _breakFast = MutableLiveData<List<ProductInformation>>()
    val breakFast: LiveData<List<ProductInformation>> = _breakFast

    private val _lunch = MutableLiveData<List<ProductInformation>>()
    val lunch: LiveData<List<ProductInformation>> = _lunch

    private val _dinner = MutableLiveData<List<ProductInformation>>()
    val dinner: LiveData<List<ProductInformation>> = _dinner


    fun setMealList(){

    }

}