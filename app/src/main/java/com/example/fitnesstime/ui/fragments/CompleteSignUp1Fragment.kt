package com.example.fitnesstime.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentCompleteSignUp1Binding
import com.example.fitnesstime.ui.viewmodel.UserSignUpInformationViewModel
import com.example.fitnesstime.utilities.Validator
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class CompleteSignUp1Fragment : Fragment() {

    private lateinit var binding: FragmentCompleteSignUp1Binding
    private val sharedViewModel: UserSignUpInformationViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCompleteSignUp1Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            sharedViewModel.setBirthDate(updateLabel().format(myCalendar.time))
            Toast.makeText(requireContext(), "Inserted the following Date: " + updateLabel().format(myCalendar.time), Toast.LENGTH_LONG).show()
        }





        //updateLabel(myCalendar).format(myCalendar.time)

        binding.apply {

            completeAge.setOnClickListener {
                DatePickerDialog(requireActivity() , datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            }

            with(sharedViewModel) {
                birthDate.observe(requireActivity(), Observer {
                    calculateAge(it.toString())
                    })
                age.observe(requireActivity(), Observer {
                        completeAgeValue.text = (it.toString())
                    })
            }



            viewModel = sharedViewModel


            completesignup1next.setOnClickListener {

                //Checking if input are not empty
                if (completeAgeValue.text.isNotBlank()) {

                    if (Validator.validateInput(completeHeight, requireContext())) {

                        //Fill User View Model with Data
                        sharedViewModel.apply {
                            //setAge(completeAge.text.toString().toInt())
                            setHeight(completeHeight.text.toString().toFloat())
                        }

                        //Moving to complete sign up 2!
                        findNavController().navigate(R.id.action_completeSignUp1Fragment_to_completeSignUp2Fragment)
                    } else
                        Toast.makeText(
                            activity,
                            "Please fill all the required information",
                            Toast.LENGTH_SHORT
                        ).show()
                }

            }
        }
        super.onStart()
    }
    private fun updateLabel(): SimpleDateFormat{
        val myFormat = "yyyy-MM-dd"
        return SimpleDateFormat(myFormat, Locale.getDefault())
    }
}