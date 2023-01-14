package com.example.fitnesstime.ui.fragments


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentDashboardBinding
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import com.example.fitnesstime.ui.viewmodel.UserSignUpInformationViewModel
import com.example.fitnesstime.ui.viewmodel.UserTrackingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DashboardFragment : Fragment()/*, SensorEventListener*/ {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedViewModel: UserTrackingViewModel by activityViewModels()
    private var userRepo = UserAccountInformationRepository(RetrofitInstance.retrofit)
   // private var sensorManager: SensorManager? = null
   // private var running = false
    //private var totalSteps = 0f
    //private var previousTotalSteps = 0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //sharedViewModel.setBaseGoal()
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //findNavController().setLifecycleOwner(this)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true
        //sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)

        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        sharedViewModel.baseGoalCalories.observe(requireActivity(), Observer {
            binding.dashboardBaseGoalCalories.text = sharedViewModel.baseGoalCalories.value!!.toString()
        })
        sharedViewModel.consumedCalories.observe(requireActivity(), Observer {
            binding.dashboardConsumedCalories.text = sharedViewModel.consumedCalories.value!!.toString()
        })

        /*running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(context, "No Sensor Detected on this device", Toast.LENGTH_SHORT).show()
        }else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }*/
    }

    /*override fun onSensorChanged(p0: SensorEvent?) {
        if(running)
            p0?.let {
                totalSteps = p0.values[0]
            }
        val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }*/


}