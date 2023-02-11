package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentMacrosBinding
import com.example.fitnesstime.ui.model.AllMealsInformationAndQuantity
import com.example.fitnesstime.ui.repositories.MealProductRepository
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MacrosFragment : Fragment() {


    private lateinit var binding: FragmentMacrosBinding

    private val carbohydrates = MutableLiveData(0f)
    private val fat = MutableLiveData(0f)
    private val protein = MutableLiveData(0f)

    private val carbohydratesGoal = MutableLiveData(0)
    private val fatGoal = MutableLiveData(0)
    private val proteinGoal = MutableLiveData(0)

    private val baseGoal = MutableLiveData(0)

    private var breakFast= MutableLiveData<List<AllMealsInformationAndQuantity>?>()
    private var lunch= MutableLiveData<List<AllMealsInformationAndQuantity>?>()
    private var dinner= MutableLiveData<List<AllMealsInformationAndQuantity>?>()

    private val api = RetrofitInstance.retrofit
    private val mealProductRepo = MealProductRepository(api)
    private val userRepo = UserAccountInformationRepository(api)
    private val diaryRepo = UserAccountInformationRepository(api)

    private lateinit var sharedPreferences: SharedPreferences



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { binding = FragmentMacrosBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
        carbohydrates.value = 0f
        protein.value = 0f
        fat.value = 0f
            getLists()
        setupPieCHart()
        loadPieChartData()

        breakFast.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()){
                calculatePercentages()
            }
        })
        dinner.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()){
                calculatePercentages()
            }
        })
        lunch.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()){
                calculatePercentages()
            }
        })

        protein.observe(requireActivity(), Observer {
            if (it != null) {
                if (it >= 0.0f)
                    binding.proteinTotalConsumed.text = it.toInt().toString()+ '%'

            }
        })

        carbohydrates.observe(requireActivity(), Observer {
            if (it != null) {
                if (it >= 0.0f)
                    binding.carbohydratesTotalConsumed.text = it.toInt().toString()+ '%'
            }
        })

        fat.observe(requireActivity(), Observer {
            if (it != null) {
                if (it >= 0.0f)
                    binding.fatTotalConsumed.text = it.toInt().toString()+ '%'
            }
        })

        fatGoal.observe(requireActivity(), Observer {
            if (it != null) {
                if(it > 0)
                    binding.apply {

                        fatTotalGoal.text = fatGoal.value.toString() + '%'

                    }

            }
        })

        carbohydratesGoal.observe(requireActivity(), Observer {
            if (it != null) {
                if(it > 0)
                    binding.apply {
                        carbohydratesTotalGoal.text = carbohydratesGoal.value.toString() + '%'

                    }

            }
        })

        proteinGoal.observe(requireActivity(), Observer {
            if (it != null) {
                if(it > 0)
                    binding.apply {
                        proteinTotalGoal.text = proteinGoal.value.toString() + '%'
                    }

            }
        })


        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLists(){
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrBlank()) {
            GlobalScope.launch(Dispatchers.IO){
                val response = userRepo.getMacros(email)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful)
                        if (response.body()!!.Success) {
                            carbohydratesGoal.value = response.body()!!.Data!!.carbohydrates.toInt()
                            fatGoal.value = response.body()!!.Data!!.fat.toInt()
                            proteinGoal.value = response.body()!!.Data!!.protein.toInt()
                        }
                }
            }
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            //if (breakFast.value.isNullOrEmpty())
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

            //if (lunch.value.isNullOrEmpty())
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

            //if (dinner.value.isNullOrEmpty())
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

    fun calculatePercentages() {
        var totalProtein = 0
        var totalCarbohydrates = 0
        var totalFat = 0
        var totalCalories = 0

        breakFast.value?.forEach {
            totalProtein += it.Protein.toSafeMacroInt()
            totalCarbohydrates += it.Total_Carbs.toSafeMacroInt()
            totalFat += it.Total_Fat.toSafeMacroInt()
            //totalCalories += it.calories.toSafeMacroInt()
        }

        lunch.value?.forEach {
            totalProtein += it.Protein.toSafeMacroInt()
            totalCarbohydrates += it.Total_Carbs.toSafeMacroInt()
            totalFat += it.Total_Fat.toSafeMacroInt()
            //totalCalories += it.calories.toSafeMacroInt()
        }

        dinner.value?.forEach {
            totalProtein += it.Protein.toSafeMacroInt()
            totalCarbohydrates += it.Total_Carbs.toSafeMacroInt()
            totalFat += it.Total_Fat.toSafeMacroInt()
            //totalCalories += it.calories.toSafeMacroInt()
        }

        totalCalories = totalProtein + totalCarbohydrates + totalFat
        val proteinPercentage = (totalProtein.toFloat() / totalCalories.toFloat()) * 100
        val carbohydratesPercentage = (totalCarbohydrates.toFloat() / totalCalories.toFloat()) * 100
        val fatPercentage = (totalFat.toFloat() / totalCalories.toFloat()) * 100
        protein.value = proteinPercentage
        carbohydrates.value = carbohydratesPercentage
        fat.value = fatPercentage
        setupPieCHart()
        loadPieChartData()
    }

    private fun setupPieCHart(){
        binding.macrosRatios.isDrawHoleEnabled = true
        binding.macrosRatios.setUsePercentValues(false)
        binding.macrosRatios.setEntryLabelTextSize(7f)
        binding.macrosRatios.setEntryLabelColor(Color.BLACK)
        binding.macrosRatios.centerText = "Macros"
        binding.macrosRatios.setCenterTextSize(15f)
        binding.macrosRatios.description.isEnabled = false

        var l = binding.macrosRatios.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.isEnabled = true

    }

    private fun loadPieChartData(){
        var entries = ArrayList<PieEntry>()
        entries.add(PieEntry(protein.value!!.toFloat(), "Protein"))
        entries.add(PieEntry(fat.value!!.toFloat(), "Fat"))
        entries.add(PieEntry(carbohydrates.value!!.toFloat(), "Carbohydrates"))

        var colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS)
            colors.add(color)
        for (color in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(color)

        var dataSet = PieDataSet(entries, "")
        dataSet.setColors(colors)

        var data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(binding.macrosRatios))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)

        binding.macrosRatios.data = data
        binding.macrosRatios.invalidate()

    }
}
fun String?.toSafeMacroInt(): Int{
    if(this == null)
        return 0
    return try {
        toInt()

    }
    catch (ex: Exception){
        0
    }
}