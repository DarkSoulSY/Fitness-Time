package com.example.fitnesstime.ui.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance

import com.example.fitnesstime.ui.model.AllMealsInformationAndQuantity
import com.example.fitnesstime.ui.repositories.MealProductRepository
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DashboardViewModel : ViewModel() {

    private val api = RetrofitInstance.retrofit

    private val mealProductRepository: MealProductRepository = MealProductRepository(api)

    private val userAccountInformationRepository: UserAccountInformationRepository = UserAccountInformationRepository(api)

    private val _baseGoal = MutableLiveData<Int>(0)
    val baseGoal: LiveData<Int> = _baseGoal

    private val _consumedCalories = MutableLiveData<Int>(0)
    val consumedCalories: LiveData<Int> = _consumedCalories

    private val _remainingCalories = MutableLiveData<Int>()
    val remainingCalories: LiveData<Int> = _remainingCalories

    private val _breakFast = MutableLiveData<List<AllMealsInformationAndQuantity>>()
    val breakFast: LiveData<List<AllMealsInformationAndQuantity>> = _breakFast

    private val _lunch = MutableLiveData<List<AllMealsInformationAndQuantity>>()
    val lunch: LiveData<List<AllMealsInformationAndQuantity>> = _lunch

    private val _dinner = MutableLiveData<List<AllMealsInformationAndQuantity>>()
    val dinner: LiveData<List<AllMealsInformationAndQuantity>> = _dinner

    fun calculateCalories(list1: List<AllMealsInformationAndQuantity>?, list2: List<AllMealsInformationAndQuantity>?, list3: List<AllMealsInformationAndQuantity>?) {
        var totalCalories = 0
        list1?.let {
            totalCalories += it.sumOf { product -> product.calories.toInt() * product.quantity.toInt() }
        }
        list2?.let {
            totalCalories += it.sumOf { product -> product.calories.toInt() * product.quantity.toInt() }
        }
        list3?.let {
            totalCalories += it.sumOf { product -> product.calories.toInt() * product.quantity.toInt() }
        }
        _consumedCalories.value = totalCalories
        setRemainingCalories()
    }

    fun setRemainingCalories(){
        _remainingCalories.value = baseGoal.value?.minus(consumedCalories.value!!)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun setBaseGoal(context: Context, email: String){
        GlobalScope.launch(Dispatchers.IO) {
            val response = userAccountInformationRepository.getBaseGoal(email)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _baseGoal.value = response.body()!!.Data!!.toInt()
                    //Toast.makeText(context,response.body()!!.Message + response.body()!!.Data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)

    fun getLists(email: String){
        if (!email.isNullOrBlank()) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            GlobalScope.launch(Dispatchers.IO){
                val response = userAccountInformationRepository.getBaseGoal(email)
                withContext(Dispatchers.Main){
                    if(response.isSuccessful)
                        if (response.body()!!.Data != null)
                            _baseGoal.value = response.body()!!.Data!!.toInt()
                }
            }
            if (breakFast.value.isNullOrEmpty())
                GlobalScope.launch(Dispatchers.IO) {
                    val response = mealProductRepository.getMeals(
                        "breakfast",
                        email,
                        LocalDateTime.now().format(formatter).toString()
                    )
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful)
                            if (response.body()!!.Success) {
                                _breakFast.value = response.body()!!.Data!!
                            }
                    }
                }
            if (lunch.value.isNullOrEmpty())
                GlobalScope.launch(Dispatchers.IO) {
                    val response = mealProductRepository.getMeals(
                        "lunch",
                        email,
                        LocalDateTime.now().format(formatter).toString()
                    )
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful)
                            if (response.body()!!.Success) {
                                _lunch.value = response.body()!!.Data!!


                            }
                    }
                }
            if (dinner.value.isNullOrEmpty())
                GlobalScope.launch(Dispatchers.IO) {
                    val response = mealProductRepository.getMeals(
                        "dinner",
                        email,
                        LocalDateTime.now().format(formatter).toString()
                    )
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful)
                            if (response.body()!!.Success) {
                                _dinner.value = response.body()!!.Data!!
                            }
                    }
                }
        }
    }
}