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
import com.example.fitnesstime.databinding.FragmentCompleteSignUp2Binding
import com.google.android.material.bottomnavigation.BottomNavigationView


class CompleteSignUp2Fragment : Fragment() {

    private lateinit var binding: FragmentCompleteSignUp2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompleteSignUp2Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true
        binding.completesignup2next.setOnClickListener {
            findNavController().navigate(R.id.action_completeSignUp2Fragment_to_completeSignUp3Fragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}