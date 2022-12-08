package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentSignUpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true

        binding.alreadyhaveanaccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        binding.signupnext.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_completeSignUp1Fragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}