package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentFinishSignUpBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class FinishSignUpFragment : Fragment() {

    private lateinit var binding: FragmentFinishSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFinishSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true

        binding.finishsignupdone.setOnClickListener {
            findNavController().navigate(R.id.action_finishSignUpFragment_to_dashboardFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }


}