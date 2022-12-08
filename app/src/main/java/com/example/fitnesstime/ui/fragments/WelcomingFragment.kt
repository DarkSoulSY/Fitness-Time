package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentWelcomingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class WelcomingFragment : Fragment() {

    private lateinit var binding: FragmentWelcomingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true


        binding.btnsignin.setOnClickListener {

            findNavController().navigate(R.id.action_welcomingFragment_to_signInFragment)

        }

        binding.btnsignup.setOnClickListener {
            findNavController().navigate(R.id.action_welcomingFragment_to_signUpFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }


}