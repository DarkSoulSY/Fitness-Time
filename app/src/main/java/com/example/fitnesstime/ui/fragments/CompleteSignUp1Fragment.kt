package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentCompleteSignUp1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView


class CompleteSignUp1Fragment : Fragment() {

    private lateinit var binding: FragmentCompleteSignUp1Binding
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
        binding.completesignup1next.setOnClickListener {
            findNavController().navigate(R.id.action_completeSignUp1Fragment_to_completeSignUp2Fragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}