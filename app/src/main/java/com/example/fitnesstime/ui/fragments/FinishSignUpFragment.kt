package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentFinishSignUpBinding
import com.example.fitnesstime.ui.viewmodel.UserSignUpInformationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class FinishSignUpFragment : Fragment() {

    private lateinit var binding: FragmentFinishSignUpBinding
    private val sharedViewModel: UserSignUpInformationViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            activity?.let { it1 -> sharedViewModel.createAccountAndAssociatedPreferences(it1.applicationContext)}
        } catch (e: Exception) {

        }

        // Inflate the layout for this fragment
        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
        binding = FragmentFinishSignUpBinding.inflate(inflater, container, false)
        val observer = Observer<Boolean> {
            if (it) {
                sharedViewModel.success.removeObservers(requireActivity())
                sharedPreferences.edit().putString("Email", sharedViewModel.email.value).apply()
                sharedPreferences.edit().putString("Password", sharedViewModel.password.value).apply()
                sharedPreferences.edit().putBoolean("Logged", true).apply()
            }
        }
        sharedViewModel.success.observe(requireActivity(), observer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true


        super.onViewCreated(view, savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        binding.apply {

            finishWelcome.text = "Welcome " + sharedViewModel.firstName.value!!.lowercase(Locale.ROOT).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } + '!'

            finishsignupdone.setOnClickListener {
                if (sharedViewModel.success.value!!)
                    findNavController().navigate(R.id.action_finishSignUpFragment_to_dashboardFragment)
                }
            }


        }

}
