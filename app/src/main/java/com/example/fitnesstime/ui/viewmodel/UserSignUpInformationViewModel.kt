package com.example.fitnesstime.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.enum.ActivityLevel
import com.example.fitnesstime.enum.Gender
import com.example.fitnesstime.enum.WeightPlanType
import com.example.fitnesstime.ui.model.CreateAccountNPreferences
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


    @OptIn(DelicateCoroutinesApi::class)
    fun createAccountAndAssociatedPreferences(context: Context){
        val cGoal = calculateCalories(weight.value!!,
            height.value!!,
            age.value!!,
            gender.value!!,
            weightPlanType.value!!,
            weeklyActivity.value!!,
            ratio.value)

        if(ratio.value == null)
            _goalWeight.value = weight.value

        val createAccountNPreferences = CreateAccountNPreferences(
            first_name = firstName.value!!.toLowerCase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            last_name = lastName.value!!.toLowerCase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            email = email.value!!.lowercase(),
            password = password.value!!,
            gender = gender.value!!.toLowerCase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            height = height.value!!,
            weight = weight.value!!,
            birthday = birthDate.value!!.replace('-', ':'),
            phone = phoneNumber.value!!,
            weight_plan_type = weightPlanType.value!!.toLowerCase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            weight_goal = goalWeight.value!!,
            weekly_activity = weeklyActivity.value!!.toLowerCase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            caloric_plan_goal = cGoal)

        //viewModelScope.launch {
        job = GlobalScope.launch(Dispatchers.IO) {
            val response = userAccountInformationRepository.createAccountNPreferences(createAccountNPreferences)
            withContext(Dispatchers.Main){
                response.apply {
                    if(isSuccessful)
                        body()!!.apply {
                            if(Success) {
                                Toast.makeText(context, Message, Toast.LENGTH_SHORT)
                                success.value = response.body()!!.Success
                            }
                            else {
                                Toast.makeText(context, Message, Toast.LENGTH_SHORT)
                                success.value = response.body()!!.Success
                            }
                        }
                }
            }
        }
    }
    /*private fun calculateCalories(weight: Float, height: Float, age: Int, gender: String, plan: String, activityLevel: String): Int {

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
    }*/
    private fun calculateCalories(weight: Float, height: Float, age: Int,
    gender: String, plan: String, activityLevel: String, percent: Float? = null): Int {

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
        var percentage: Int = 0
        if(percent != null)
            percentage= when(percent){
                0.2f -> 220
                0.5f -> 550
                0.8f -> 880
                1.0f -> 1100
                else -> {throw AssertionError()}
            }

        val maintenanceCalories = baseCalories * activityFactor
        if(plan != WeightPlanType.MAINTAIN_WEIGHT.description && percent == null)
            throw AssertionError("Percent is required for Gain or Lose weight")
        return when (plan) {
            WeightPlanType.MAINTAIN_WEIGHT.description -> maintenanceCalories.toInt()
            WeightPlanType.GAIN_WEIGHT.description -> (maintenanceCalories + percentage).toInt()
            WeightPlanType.LOSE_WEIGHT.description -> (maintenanceCalories - percentage).toInt()
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
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}