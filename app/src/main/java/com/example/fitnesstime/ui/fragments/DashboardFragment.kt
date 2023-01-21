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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesstime.R
import com.example.fitnesstime.adapters.BreakFastAdapter
import com.example.fitnesstime.adapters.DinnerAdapter
import com.example.fitnesstime.adapters.LunchAdapter
import com.example.fitnesstime.databinding.FragmentDashboardBinding
import com.example.fitnesstime.ui.viewmodel.DashboardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardFragment : Fragment()/*, SensorEventListener*/ {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedViewModel: DashboardViewModel by activityViewModels()

    //private var userRepo = UserAccountInformationRepository(RetrofitInstance.retrofit)
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

        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)

        val observer = Observer<Int>{
            if(it != 0) {
                binding.dashboardBaseGoalCalories.text = sharedViewModel.baseGoal.value!!.toString()
            }
        }
        sharedViewModel.baseGoal.observe(requireActivity(), observer)

        /*sharedViewModel.consumedCalories.observe(requireActivity(), Observer {
            binding.dashboardConsumedCalories.text = sharedViewModel.consumedCalories.value!!.toString()
        })*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //findNavController().setLifecycleOwner(this)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true

        //sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrBlank()){
            sharedViewModel.setBaseGoal(requireContext(), email)
            sharedViewModel.getLists(email)

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

                consumedCalories.observe(requireActivity(), Observer {
                    if(it >= 0) {
                        binding.dashboardConsumedCalories.text = consumedCalories.value.toString()
                        if(consumedCalories.value != null && consumedCalories.value != 0 && baseGoal.value != null && baseGoal.value != 0)
                            binding.dashboardCircleRemainingCalories.text = (baseGoal.value?.minus(consumedCalories.value!!)).toString() + "\nRemaining"
                            binding.caloriesProgressbar.progress =  ((consumedCalories.value!!.toFloat() / baseGoal.value!!.toFloat()) * 100).toInt()
                    }
                })
            }
        }


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