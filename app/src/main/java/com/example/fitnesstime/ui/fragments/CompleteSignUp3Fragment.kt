package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentCompleteSignUp3Binding
import com.example.fitnesstime.ui.model.viewmodel.UserSignUpInformationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class CompleteSignUp3Fragment : Fragment() {

    private lateinit var binding: FragmentCompleteSignUp3Binding
    private val sharedViewModel: UserSignUpInformationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompleteSignUp3Binding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {

        binding.apply {
            completesignup3next.setOnClickListener {
                //getting male female radio input
                val activity = complete3Activity.checkedRadioButtonId
                val selectedActivity = resources.getResourceEntryName(activity)

                //saving input into a bundle
                sharedViewModel.setWeeklyActivity(selectedActivity)

                //Move to next Activity
                findNavController().navigate(R.id.action_completeSignUp3Fragment_to_finishSignUpFragment)
            }

        }

        super.onStart()
    }
}