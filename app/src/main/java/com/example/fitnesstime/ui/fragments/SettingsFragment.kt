package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentSettingsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {// Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        binding.apply {
            settingsProfile.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_profileFragment)
            }
            settingsAbout.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
            }
            settingsCalories.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_editMacrosAndCaloriesFragment)
            }
            settingsMacros.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_macrosFragment)
            }
        }

        super.onStart()
    }

}