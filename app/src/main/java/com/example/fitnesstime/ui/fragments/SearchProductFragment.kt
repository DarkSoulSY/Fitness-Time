package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentSearchProductBinding
import com.example.fitnesstime.ui.model.SingleProductNutritionInformation
import com.example.fitnesstime.ui.repositories.MealProductRepository
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SearchProductFragment : Fragment() {


    private lateinit var binding: FragmentSearchProductBinding
    //private val personalViewModel: ProductViewModel by activityViewModels()
    private lateinit var items: List<SingleProductNutritionInformation>
    private val api = RetrofitInstance.retrofit
    private var itemNameInList: Int = -1
    private var itemNoInList: String = ""
    private val mealProductRepository: MealProductRepository = MealProductRepository(api)
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchProductBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onStart() {
        super.onStart()

        val email = sharedPreferences.getString("Email", null)
        val mealType = arguments?.getString("meal_type", null)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        GlobalScope.launch(Dispatchers.IO) {
            val response = mealProductRepository.getAllProduct()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful and response.body()!!.Success) {
                    items = response.body()!!.Data!!
                }
            }
        }

        binding.apply {

            searchProductName.setOnClickListener {

                val bundle = Bundle()
                bundle.putString("Product Name", itemNoInList)
                findNavController().navigate(R.id.action_searchProductFragment_to_nutritionFactsFragment, bundle)
            }

            searchEngine.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    items.filter {
                        it.productName.lowercase(Locale.getDefault()).contains(searchEngine.text.toString().lowercase(Locale.getDefault()))
                    }.forEach {
                        itemNoInList = it.productName
                        itemNameInList = it.id.toInt()
                        searchProductName.text = it.productName
                        searchProductCalories.text = it.calories
                        searchFirstContainer.isVisible = true
                        searchDone.isVisible = true
                        searchDone.setOnClickListener {
                            if (!email.isNullOrEmpty() && !mealType.isNullOrEmpty() && itemNameInList > 0)
                            GlobalScope.launch(Dispatchers.IO){
                                val response = mealProductRepository.addProduct(mealType, email, 1, itemNameInList, LocalDateTime.now().format(formatter).toString())
                                withContext(Dispatchers.Main){
                                        if (response.isSuccessful)
                                            Toast.makeText(requireContext(), response.body()!!.Message.toString(), Toast.LENGTH_SHORT).show()

                                }
                            }
                        }
                    }
                }
            })

        }
    }

}