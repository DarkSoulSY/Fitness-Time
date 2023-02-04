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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentDashboardBinding
import com.example.fitnesstime.ui.viewmodel.DashboardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DashboardFragment : Fragment(){

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedViewModel: DashboardViewModel by activityViewModels()

    //private var userRepo = UserAccountInformationRepository(RetrofitInstance.retrofit)

    @RequiresApi(Build.VERSION_CODES.O)
    private var currentDate: MutableLiveData<LocalDate> = MutableLiveData<LocalDate>(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrBlank()){
            try {
                sharedViewModel.setBaseGoal(requireContext(), email)
                sharedViewModel.getLists(email)
            } catch (e: Exception) {

            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)



        sharedViewModel.apply {
            breakFast.observe(requireActivity(), Observer {
                if(!it.isNullOrEmpty()){
                    calculateCalories(breakFast.value,lunch.value,dinner.value)
                }
            })
            lunch.observe(requireActivity(), Observer {
                if(!it.isNullOrEmpty()){
                    calculateCalories(breakFast.value,lunch.value,dinner.value)
                }
            })
            dinner.observe(requireActivity(), Observer {
                if(!it.isNullOrEmpty()){
                    calculateCalories(breakFast.value,lunch.value,dinner.value)
                }
            })
            remainingCalories.observe(requireActivity(), Observer {
                if(it >= 0)
                    binding.dashboardCircleRemainingCalories.text = (baseGoal.value?.minus(consumedCalories.value!!)).toString() + "\nRemaining"
            })

            consumedCalories.observe(requireActivity(), Observer {

                if(it >= 0) {
                    binding.dashboardConsumedCalories.text = consumedCalories.value.toString()
                    if(consumedCalories.value != null && consumedCalories.value != 0 && baseGoal.value != null && baseGoal.value != 0)
                        binding.caloriesProgressbar.progress =  ((consumedCalories.value!!.toFloat() / baseGoal.value!!.toFloat()) * 100).toInt()
                }
            })
        }
        val observer = Observer<Int>{
            if(it != 0) {
                binding.dashboardBaseGoalCalories.text = sharedViewModel.baseGoal.value!!.toString()
                sharedViewModel.setRemainingCalories()
            }
        }
        sharedViewModel.baseGoal.observe(requireActivity(), observer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true

        super.onViewCreated(view, savedInstanceState)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

    }


}