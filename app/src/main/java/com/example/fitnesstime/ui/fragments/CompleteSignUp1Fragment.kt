package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentCompleteSignUp1Binding
import com.example.fitnesstime.ui.model.viewmodel.UserSignUpInformationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.fitnesstime.validation.Validation

class CompleteSignUp1Fragment : Fragment() {

    private lateinit var binding: FragmentCompleteSignUp1Binding
    private val sharedViewModel: UserSignUpInformationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        binding.apply {

            viewModel = sharedViewModel
            completesignup1next.setOnClickListener {

                //Checking if input are not empty
                if (Validation.validateInput(completeAge, requireContext()) && Validation.validateInput(completeHeight, requireContext())) {

                    //Fill User View Model with Data
                    sharedViewModel.apply {
                        setAge(completeAge.text.toString().toInt())
                        setHeight(completeHeight.text.toString().toFloat())
                    }

                    //Moving to complete sign up 2!
                    findNavController().navigate(R.id.action_completeSignUp1Fragment_to_completeSignUp2Fragment)
                }
                else
                    Toast.makeText(activity, "Please fill all the required information", Toast.LENGTH_SHORT).show()

            }
        }
        super.onStart()
    }
}