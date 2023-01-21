package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesstime.R
import com.example.fitnesstime.adapters.BreakFastAdapter
import com.example.fitnesstime.adapters.DinnerAdapter
import com.example.fitnesstime.adapters.LunchAdapter
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentDiaryBinding
import com.example.fitnesstime.ui.model.AllMealsInformationAndQuantity
import com.example.fitnesstime.ui.repositories.MealProductRepository
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DiaryFragment : Fragment() {

    private lateinit var binding: FragmentDiaryBinding

    private var breakFast= MutableLiveData<List<AllMealsInformationAndQuantity>?>()
    private var lunch= MutableLiveData<List<AllMealsInformationAndQuantity>?>()
    private var dinner= MutableLiveData<List<AllMealsInformationAndQuantity>?>()

    private val api = RetrofitInstance.retrofit
    private val mealProductRepo = MealProductRepository(api)
    private val userAccountInformationRepository = UserAccountInformationRepository(api)

    private lateinit var sharedPreferences: SharedPreferences
    private var consumedCalories = MutableLiveData<Int>(0)
    private var baseGoal = MutableLiveData<Int>(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDiaryBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true

        super.onViewCreated(view, savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()
        getLists()
    }
    override fun onStart() {
        super.onStart()

        //filling up our lists

        breakFast.observe(requireActivity(), Observer {
            if(!it.isNullOrEmpty()){
                calculateCalories(breakFast.value,lunch.value,dinner.value)
                binding.diaryBreakfastRecycler.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.diaryBreakfastRecycler.setHasFixedSize(true)
                binding.diaryBreakfastRecycler.adapter = BreakFastAdapter(breakFast.value!!)
            }
        })
        lunch.observe(requireActivity(), Observer {
            if(!it.isNullOrEmpty()){
                calculateCalories(breakFast.value,lunch.value,dinner.value)
                binding.diaryLunchRecyclerlunch.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.diaryLunchRecyclerlunch.setHasFixedSize(true)
                binding.diaryLunchRecyclerlunch.adapter = LunchAdapter(lunch.value!!)
            }
        })
        dinner.observe(requireActivity(), Observer {
            if(!it.isNullOrEmpty()){
                calculateCalories(breakFast.value,lunch.value,dinner.value)
                binding.diaryDinnerRecycler.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.diaryDinnerRecycler.setHasFixedSize(true)
                binding.diaryDinnerRecycler.adapter = DinnerAdapter(dinner.value!!)
            }
        })


        baseGoal.observe(requireActivity(), Observer {
            if (baseGoal.value!! > 0)
                binding.diaryBaseGoal.text = baseGoal.value.toString()
        })
        consumedCalories.observe(requireActivity(), Observer {
            if(it >= 0) {
                binding.diaryConsumedCalories.text = consumedCalories.value.toString()
                if(consumedCalories.value != null && consumedCalories.value != 0 && baseGoal.value != null && baseGoal.value != 0)
                    binding.diaryRemainingCalories.text = (baseGoal.value?.minus(consumedCalories.value!!)).toString()
            }
        })

        binding.apply {
            val bundle = Bundle()
            diaryAddBreakfastItem.setOnClickListener {

                bundle.putString("meal_type", "breakfast")

                findNavController().navigate(R.id.action_diaryFragment_to_searchProductFragment, bundle)
            }
            diaryAddLunchItem.setOnClickListener {

                bundle.putString("meal_type", "lunch")

                findNavController().navigate(R.id.action_diaryFragment_to_searchProductFragment, bundle)
            }
            diaryAddDinnerItem.setOnClickListener {

                bundle.putString("meal_type", "dinner")

                findNavController().navigate(R.id.action_diaryFragment_to_searchProductFragment, bundle)
            }


        }
    }

    private fun calculateCalories(list1: List<AllMealsInformationAndQuantity>?, list2: List<AllMealsInformationAndQuantity>?, list3: List<AllMealsInformationAndQuantity>?) {
        var totalCalories = 0
        list1?.let {
            totalCalories += it.sumOf { product -> product.calories.toSafeInt() * product.quantity.toSafeInt() }
        }
        list2?.let {
            totalCalories += it.sumOf { product -> product.calories.toSafeInt() * product.quantity.toSafeInt() }
        }
        list3?.let {
            totalCalories += it.sumOf { product -> product.calories.toSafeInt() * product.quantity.toSafeInt() }
        }
        consumedCalories.value = totalCalories
    }
    @RequiresApi(Build.VERSION_CODES.O)

    fun getLists(){
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrBlank()) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            GlobalScope.launch(Dispatchers.IO){
                val response = userAccountInformationRepository.getBaseGoal(email)
                withContext(Dispatchers.Main){
                    if(response.isSuccessful)
                        if (response.body()!!.Data != null)
                            baseGoal.value = response.body()!!.Data!!.toInt()
                }
            }
            if (breakFast.value.isNullOrEmpty())
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
            if (lunch.value.isNullOrEmpty())
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
            if (dinner.value.isNullOrEmpty())
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

        }
    }

}

fun String?.toSafeInt(): Int{
    if(this == null)
        return -1
    return try {
        toInt()

    }
    catch (ex: Exception){
        -1
    }
}