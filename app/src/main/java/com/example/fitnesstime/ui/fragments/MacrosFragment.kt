package com.example.fitnesstime.ui.fragments

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentMacrosBinding
import com.example.fitnesstime.ui.model.AllMealsInformationAndQuantity
import com.example.fitnesstime.ui.repositories.MealProductRepository
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MacrosFragment : Fragment() {


    private lateinit var binding: FragmentMacrosBinding

    private val carbohydrates = MutableLiveData<Float?>(0f)
    private val fat = MutableLiveData<Float?>(0f)
    private val protein = MutableLiveData<Float?>(0f)

    private var breakFast= MutableLiveData<List<AllMealsInformationAndQuantity>?>()
    private var lunch= MutableLiveData<List<AllMealsInformationAndQuantity>?>()
    private var dinner= MutableLiveData<List<AllMealsInformationAndQuantity>?>()

    private val api = RetrofitInstance.retrofit
    private val mealProductRepo = MealProductRepository(api)

    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { binding = FragmentMacrosBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getLists()
        breakFast.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()){
                a()
            }
        })

    }

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLists(){
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrBlank()) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            if (breakFast.value.isNullOrEmpty())
                try {
                    GlobalScope.launch(Dispatchers.IO) {
                        val response = mealProductRepo.getMeals(
                            "breakfast",
                            email,
                            LocalDateTime.now().format(formatter).toString()
                        )
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful)
                                if (response.body()!!.Success) {
                                    breakFast.value = response.body()!!.Data!!


                                }
                        }
                    }
                } catch (e: Exception) {

                }

            if (lunch.value.isNullOrEmpty())
                try {
                    GlobalScope.launch(Dispatchers.IO) {
                        val response = mealProductRepo.getMeals(
                            "lunch",
                            email,
                            LocalDateTime.now().format(formatter).toString()
                        )
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful)
                                if (response.body()!!.Success) {
                                    lunch.value = response.body()!!.Data!!


                                }
                        }

                    }
                } catch (e: Exception) {

                }

            if (dinner.value.isNullOrEmpty())
                try {
                    GlobalScope.launch(Dispatchers.IO) {
                        val response = mealProductRepo.getMeals(
                            "dinner",
                            email,
                            LocalDateTime.now().format(formatter).toString()
                        )
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful)
                                if (response.body()!!.Success) {
                                    dinner.value = response.body()!!.Data!!


                                }

                        }
                    }
                } catch (e: Exception) {

                }


        }
    }
    @SuppressLint("SetTextI18n")
    private fun a(){
        protein.value = (breakFast.value?.map { it.Protein }?.reduce { acc, protein -> acc + protein.toInt() } ?: 0) as Float?
        fat.value = (lunch.value?.map { it.Total_Fat }?.reduce { acc, fat -> acc + fat.toInt()  } ?: 0) as Float?
        carbohydrates.value = (dinner.value?.map { it.Total_Carbs }?.reduce { acc, carbs -> acc + carbs.toInt()  } ?: 0) as Float?

        val total = protein.value!! + fat.value!! + carbohydrates.value!!
        val proteinPercentage = (carbohydrates.value!! / total) * 100
        val fatPercentage = (fat.value!! / total) * 100
        val carbsPercentage = (carbohydrates.value!! / total) * 100

        binding.apply {
            carbohydratesTotalConsumed.text = carbsPercentage.toInt().toString() + "%"
            fatTotalConsumed.text = fatPercentage.toInt().toString() + "%"
            proteinTotalConsumed.text = proteinPercentage.toInt().toString() + "%"
        }
    }
}