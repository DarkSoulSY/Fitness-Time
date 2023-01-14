package com.example.fitnesstime.ui.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.ui.model.ProductInformation
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserTrackingViewModel() : ViewModel() {
    /*//Sensor
    private var sensorManager: SensorManager? = null
    private var stepCounterSensor: Sensor? = null

    val steps = MutableLiveData<Int>()
    val calories = MutableLiveData<Float>()
    val weight = MutableLiveData<Float>()
    val height = MutableLiveData<Float>()*/

    lateinit var sharedPreferences: SharedPreferences
    //api
    private val api = RetrofitInstance.retrofit
    //Repos
    private val userAccountInformationRepository = UserAccountInformationRepository(api)
    //User Data

    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _weightHistory = MutableLiveData<List<Float>>()
    val weightHistory: LiveData<List<Float>> = _weightHistory

    private val _baseGoalCalories = MutableLiveData<Int>(0)
    val baseGoalCalories: LiveData<Int> = _baseGoalCalories

    private val _consumedCalories = MutableLiveData<Int>()
    val consumedCalories: LiveData<Int> = _consumedCalories

    private val _remainingCalories = MutableLiveData<Int>()
    val remainingCalories: LiveData<Int> = _remainingCalories

    private val _breakfast = MutableLiveData<List<ProductInformation>>()
    val breakfast: LiveData<List<ProductInformation>> = _breakfast

    private val _lunch = MutableLiveData<List<ProductInformation>>()
    val lunch: LiveData<List<ProductInformation>> = _lunch

    private val _dinner = MutableLiveData<List<ProductInformation>>()
    val dinner: LiveData<List<ProductInformation>> = _dinner


    /*fun setBaseGoal(){
        GlobalScope.launch {
            val response = userAccountInformationRepository.getBaseGoal(email.value!!.toString())
            if(response.isSuccessful){
                _baseGoalCalories.value = response.body()!!.data!!.toInt()
            }
        }
    }*/

    fun setConsumedCalories(){
        _consumedCalories.value = 0
        for (item in breakfast.value!!)
            _consumedCalories.value = item.calories
        for (item in lunch.value!!)
            _consumedCalories.value = item.calories
        for (item in dinner.value!!)
            _consumedCalories.value = item.calories
    }

    fun setRemainingCalories(){
        _remainingCalories.value = baseGoalCalories.value!! - consumedCalories.value!!
    }

    fun setUserInfo(){

    }
/*
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val step = event.values[0].toInt()
            steps.value = step
            calories.value = getCaloriesFromSteps(step, weight.value!!, height.value!!)
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }
    fun startTracking(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        stepCounterSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepCounterSensor != null) {
            sensorManager?.registerListener(sensorEventListener, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopTracking() {
        sensorManager?.unregisterListener(sensorEventListener)
    }

    fun getCaloriesFromSteps(steps: Int, weight: Float, height: Float): Float {
        val caloriesPerStep = (weight * 2.2 * 0.49) / height
        return (steps * caloriesPerStep).toFloat()
    }*/
}
