package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentWelcomingBinding
import com.example.fitnesstime.network.NetworkConnection
import com.example.fitnesstime.ui.home.Main
import com.google.android.material.bottomnavigation.BottomNavigationView


class WelcomingFragment : Fragment() {

    private lateinit var binding: FragmentWelcomingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var networkConnection: NetworkConnection
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomingBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)


        networkConnection = NetworkConnection(requireActivity().application)
        networkConnection.observe(requireActivity()) { isConnected ->
            if (isConnected){
                checkLoginStatus()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true


        binding.signIn.setOnClickListener {

            findNavController().navigate(R.id.action_welcomingFragment_to_signInFragment)

        }

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_welcomingFragment_to_signUpFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkLoginStatus() {
        val email = sharedPreferences.getString("Email", null)
        val password = sharedPreferences.getString("Password", null)
        binding.apply {
            if (!email.isNullOrBlank() && !password.isNullOrBlank()) {
                //findNavController().navigate(R.id.action_welcomingFragment_to_dashboardFragment)
                findNavController().navigate(R.id.dashboardFragment)
            }
            else
                wlcFragment.visibility = View.VISIBLE
        }
    }

}
