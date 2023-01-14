package com.example.fitnesstime.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.enum.ActivityLevel
import com.example.fitnesstime.enum.Gender
import com.example.fitnesstime.enum.WeightPlanType
import com.example.fitnesstime.ui.model.CreateAccountDTO
import com.example.fitnesstime.ui.model.CreateAccountPreferencesDTO
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class UserSignUpInformationViewModel : ViewModel() {
    //Declaring our api
    private val api = RetrofitInstance.retrofit

    //Using repos
    private val userAccountInformationRepository = UserAccountInformationRepository(api)
    //job
    var job: Job? = null

    var success = MutableLiveData(false)

    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _weightPlanType = MutableLiveData<String>()
    val weightPlanType: LiveData<String> = _weightPlanType

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    private val _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String> = _birthDate

    private val _age = MutableLiveData<Int>()
    val age: LiveData<Int> = _age

    private val _height = MutableLiveData<Float>()
    val height: LiveData<Float> = _height

    private val _ratio = MutableLiveData<Float>()
    val ratio: LiveData<Float> = _ratio

    private val _weight = MutableLiveData<Float>()
    val weight: LiveData<Float> = _weight

    private val _goalWeight = MutableLiveData<Float>()
    val goalWeight: LiveData<Float> = _goalWeight

    private val _weeklyActivity = MutableLiveData<String>()
    val weeklyActivity: LiveData<String> = _weeklyActivity


    fun setFirstName(name: String) {
        _firstName.value = name
    }

    fun setLastName(name: String) {
        _lastName.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setWeightPlanType(goal: String) {
        _weightPlanType.value = goal
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setBirthDate(date: String) {
        _birthDate.value = date
    }

    fun setHeight(height: Float) {
        _height.value = height
    }

    fun setRatio(ratio: Float) {
        _ratio.value = ratio
    }

    fun setWeight(name: Float) {
        _weight.value = name
    }

    fun setGoalWeight(goal: Float) {
        _goalWeight.value = goal
    }

    fun setWeeklyActivity(activity: String) {
        _weeklyActivity.value = activity
    }


    fun createAccountAndAssociatedPreferences(context: Context){
        Log.v("viewModel success 1", success.toString())
        val createAccountDTO = CreateAccountDTO(
            firstName.value!!,
            lastName.value!!,
            email.value!!,
            password.value!!,
            gender.value!!,
            height.value!!,
            weight.value!!,
            birthDate.value!!.replace('-', ':'),
            phoneNumber.value!!
        )

        val cGoal = calculateCalories(
            weight.value!!,
            height.value!!,
            age.value!!,
            gender.value!!,
            weightPlanType.value!!,
            weeklyActivity.value!!
        )

        val createAccountPreferencesDTO = CreateAccountPreferencesDTO(
            email = email.value!!,
            weight_plan_type = weightPlanType.value!!,
            weight_goal = goalWeight.value!!,
            weekly_activity = weeklyActivity.value!!,
            caloric_plan_goal = cGoal
        )
        //viewModelScope.launch {
        job = GlobalScope.launch(Dispatchers.IO) {
            val response = async { userAccountInformationRepository.createAccountPost(createAccountDTO) }.await()
            withContext(Dispatchers.Main){
                if(response.isSuccessful)
                {
                    Toast.makeText(context,response.body()!!.Message.toString(),Toast.LENGTH_SHORT).show()
                    success.value = response.body()!!.Success
                }
                else{
                    onError(context, response.body()!!.Message.toString())
                }
            }
            Log.v("viewModel success 2", success.toString())

            val response2 = async { delay(1000L)
                userAccountInformationRepository.createAccountPreferences(createAccountPreferencesDTO) }.await()
            withContext(Dispatchers.Main)
            {
                if(response2.isSuccessful){
                    Toast.makeText(context,response2.body()!!.Message.toString(),Toast.LENGTH_SHORT).show()
                    success.value = response.body()!!.Success
                }
                else{
                    onError(context, response.body()!!.Message.toString())
                }
            }
            Log.v("viewModel success 3", success.toString())
        }
        Log.v("viewModel success 4", success.toString())

    }
    private fun calculateCalories(weight: Float, height: Float, age: Int, gender: String, plan: String, activityLevel: String): Int {

        val baseCalories: Float = when (gender) {
            Gender.Male.name -> 10 * weight + 6.25f * height - 5 * age + 5
            Gender.Female.name -> 10 * weight + 6.25f * height - 5 * age - 161
            else -> throw AssertionError()
        }

        val activityFactor: Double = when (activityLevel) {
            ActivityLevel.NOT_VERY_ACTIVE.description -> 1.375
            ActivityLevel.LIGHTLY_ACTIVE.description -> 1.55
            ActivityLevel.ACTIVE.description -> 1.725
            ActivityLevel.VERY_ACTIVE.description -> 1.9
            else -> throw AssertionError()
        }


        val maintenanceCalories = baseCalories * activityFactor
        return when (plan) {
            WeightPlanType.MAINTAIN_WEIGHT.description -> maintenanceCalories.toInt()
            WeightPlanType.GAIN_WEIGHT.description -> (maintenanceCalories + 250).toInt()
            WeightPlanType.LOSE_WEIGHT.description -> (maintenanceCalories - 250).toInt()
            else -> throw AssertionError()
        }
    }

    fun calculateAge(dobString: String) {
        val dob = Calendar.getInstance()
        dob.time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dobString) as Date
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        _age.value = age
    }

    private fun onError(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}