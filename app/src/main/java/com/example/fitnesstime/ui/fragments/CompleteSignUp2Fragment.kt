package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.utilities.Validator
import com.example.fitnesstime.databinding.FragmentCompleteSignUp2Binding
import com.example.fitnesstime.enum.WeightPlanType
import com.example.fitnesstime.ui.viewmodel.UserSignUpInformationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class CompleteSignUp2Fragment : Fragment() {

    private lateinit var binding: FragmentCompleteSignUp2Binding
    private val sharedViewModel: UserSignUpInformationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCompleteSignUp2Binding.inflate(inflater, container, false)
        if (sharedViewModel.weightPlanType.value != WeightPlanType.MAINTAIN_WEIGHT.description) {
            binding.viewGroup1.isGone = false
            binding.viewGroup3.isGone = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {

        binding.apply {

            viewModel = sharedViewModel
            completesignup2next.setOnClickListener {
                //Check if input are empty
                if (sharedViewModel.weightPlanType.value != WeightPlanType.MAINTAIN_WEIGHT.description) {
                    if (Validator.validateInput(
                            complete2Weight,
                            requireContext()
                        ) && Validator.validateInput(complete2GoalWeight, requireContext())
                    ) {

                        //Fill User View Model with Data
                        sharedViewModel.apply {

                            setWeight(complete2Weight.text.toString().toFloat())
                            setGoalWeight(complete2GoalWeight.text.toString().toFloat())
                        }

                        //Move to next fragment
                        findNavController().navigate(R.id.action_completeSignUp2Fragment_to_completeSignUp3Fragment)
                    } else
                        Toast.makeText(
                            activity,
                            "Please fill all the required information",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                else if (sharedViewModel.weightPlanType.value == WeightPlanType.MAINTAIN_WEIGHT.description)
                    if (Validator.validateInput(complete2Weight, requireContext())) {
                        //Fill User View Model with Data
                        sharedViewModel.apply {

                            setWeight(complete2Weight.text.toString().toFloat())

                        }
                        //Move to next fragment
                        findNavController().navigate(R.id.action_completeSignUp2Fragment_to_completeSignUp3Fragment)
                    } else
                        Toast.makeText(
                            activity,
                            "Please fill all the required information",
                            Toast.LENGTH_SHORT
                        ).show()

            }

        }

        super.onStart()
    }
}