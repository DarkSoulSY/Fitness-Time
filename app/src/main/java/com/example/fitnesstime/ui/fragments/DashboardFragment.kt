package com.example.fitnesstime.ui.fragments


import android.content.Context.MODE_PRIVATE
import android.content.Context.SENSOR_SERVICE
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentDashboardBinding
import com.example.fitnesstime.ui.viewmodel.DashboardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardFragment : Fragment(), SensorEventListener{

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedViewModel: DashboardViewModel by activityViewModels()

    //private var userRepo = UserAccountInformationRepository(RetrofitInstance.retrofit)

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

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
        sensorManager = requireActivity().getSystemService(SENSOR_SERVICE) as SensorManager
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

        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(context, "No Sensor Detected on this device", Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if(running)
            p0?.let {
                totalSteps = p0.values[0]
            }
        val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    fun resetSteps(){
        previousTotalSteps = totalSteps
    }




}