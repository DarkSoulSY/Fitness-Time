package com.example.fitnesstime.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.ui.model.ServiceResponse
import com.example.fitnesstime.ui.model.SingleProductNutritionInformation
import com.example.fitnesstime.ui.repositories.MealProductRepository
import kotlinx.coroutines.*

class ProductViewModel : ViewModel() {

    private val api = RetrofitInstance.retrofit

    private val mealProductRepository: MealProductRepository = MealProductRepository(api)

    val productName = MutableLiveData<String>()

    val mealType = MutableLiveData<String>()


   /* @OptIn(DelicateCoroutinesApi::class)
    fun getSingleProduct(productName: String): ServiceResponse<List<SingleProductNutritionInformation>?>
    {
        GlobalScope.launch(Dispatchers.IO){
            val response = mealProductRepository.getSingleProduct(productName)
            withContext(Dispatchers.Main){
                if (response.isSuccessful)
                        body = response.body()!!

            }
        }
       return body
    }*/
}