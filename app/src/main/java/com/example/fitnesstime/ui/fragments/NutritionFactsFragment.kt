package com.example.fitnesstime.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.fitnesstime.R
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentNutritionFactsBinding
import com.example.fitnesstime.ui.model.ServiceResponse
import com.example.fitnesstime.ui.model.SingleProductNutritionInformation
import com.example.fitnesstime.ui.repositories.MealProductRepository
import com.example.fitnesstime.ui.viewmodel.ProductViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class NutritionFactsFragment : Fragment() {

    private lateinit var binding: FragmentNutritionFactsBinding
    private val personalViewModel: ProductViewModel by activityViewModels()
    private val api = RetrofitInstance.retrofit
    private val mealProductRepository = MealProductRepository(api)
    private lateinit var sharedPreferences:SharedPreferences

    private val spinner: Spinner? = activity?.findViewById<Spinner>(R.id.nutrition_meal)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNutritionFactsBinding.inflate(inflater, container, false)
        setupSpinner()
        sharedPreferences = requireActivity().getSharedPreferences("User Session", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true

        binding.showHideFacts.setOnClickListener {
            showHide()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val email = sharedPreferences.getString("Email", null)
        val mealType = arguments?.getString("meal_type", null)
        val date = arguments?.getString("date",null)
        val productName = arguments?.getString("Product Name", null)
        val productId = arguments?.getInt("Product Id", -1)

        binding.nutritionAddItem.setOnClickListener{
            if (!email.isNullOrEmpty() && !mealType.isNullOrEmpty() && date != null&& productId != null)
                try {
                    GlobalScope.launch(Dispatchers.IO){
                        val response = mealProductRepository.addProduct(mealType, email, 1, productId, date)
                        withContext(Dispatchers.Main){
                            if (response.isSuccessful)
                                Toast.makeText(requireContext(), response.body()!!.Message.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show()
                }
        }
    }
    override fun onStart() {
        super.onStart()
        var body : MutableLiveData<ServiceResponse<SingleProductNutritionInformation>> = MutableLiveData<ServiceResponse<SingleProductNutritionInformation>>()

        if(!requireArguments().isEmpty)
            try {
                GlobalScope.launch(Dispatchers.IO){
                    val response = mealProductRepository.getOneProduct(requireArguments().getString("Product Name", null))
                    withContext(Dispatchers.Main){
                        if (response.isSuccessful){
                            if (response.body()!!.Success)
                                body.value = response.body()!!
                        }
                    }
                }
            } catch (e: Exception) {

            }

        //val body = personalViewModel.getSingleProduct(this.requireArguments().getString("ProductName", null))
        binding.apply {

            val observer = Observer<ServiceResponse<SingleProductNutritionInformation>>{
                if (it.Success) {
                        nutritionProductName.text = it.Data?.productName
                        nutritionCalories.text = it.Data?.calories
                        nutritionSaturated.text = it.Data?.saturated
                        nutritionTotalFat.text = it.Data?.totalFat
                        nutritionTrans.text = it.Data?.trans
                        nutritionPolyunsaturated.text = it.Data?.polyunsaturated
                        nutritionMonounsaturated.text = it.Data?.monounsaturated
                        nutritionCholesterol.text = it.Data?.cholesterol
                        nutritionSodium.text = it.Data?.sodium
                        nutritionDietaryFiber.text = it.Data?.fiber
                        nutritionTotalCarbs.text = it.Data?.carbohydrates
                        nutritionSugar.text = it.Data?.sugar
                        nutritionAddedSugar.text = it.Data?.addedSugar
                        nutritionSugarAlcohols.text = it.Data?.sugarAlcohols
                        nutritionProtein.text = it.Data?.protein
                        nutritionVitamineD.text = it.Data?.vitamin_d
                        nutritionCalcium.text = it.Data?.calcium
                        nutritionIron.text = it.Data?.iron
                        nutritionVitamineA.text = it.Data?.vitamin_a
                        nutritionVitamineC.text = it.Data?.vitamin_c

                    }
            }
            body.observe(requireActivity(), observer)

        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                parent.selectedItemId
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }


    private fun showHide() {
        if (binding.showHideFacts.text == "Show") {
            binding.nutritionFactsTable.isVisible = true
            binding.showHideFacts.text = "Hide"
        }
        else {
            binding.nutritionFactsTable.isVisible = false
            binding.showHideFacts.text = "Show"
        }


    }

    private fun setupSpinner(){

        // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(requireActivity(), R.array.meal_type, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner?.adapter = adapter
        }
    }
}