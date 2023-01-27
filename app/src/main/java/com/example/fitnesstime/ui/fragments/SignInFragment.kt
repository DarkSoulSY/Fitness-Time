package com.example.fitnesstime.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentSignInBinding
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var sharedPreferences: SharedPreferences

    private var userRepo = UserAccountInformationRepository(RetrofitInstance.retrofit)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true
        super.onViewCreated(view, savedInstanceState)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStart() {
        binding.signin.setOnClickListener {
            val email = binding.signinEmail.text.toString()
            val password = binding.signinPassword.text.toString()

            try {
                GlobalScope.launch(Dispatchers.IO){
                    val response = userRepo.signIn(email, password)
                    withContext(Dispatchers.Main){
                        if (response.body()!!.Success){
                            sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
                            sharedPreferences.edit().putString("Email", email).apply()
                            sharedPreferences.edit().putString("Password", password).apply()
                            sharedPreferences.edit().putBoolean("Logged", true).apply()
                            Toast.makeText(requireContext(),response.body()!!.Message.toString(),Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signInFragment_to_dashboardFragment)
                        }
                        else{
                            sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
                            sharedPreferences.edit().putBoolean("Logged", false).apply()
                            Toast.makeText(requireContext(),response.body()!!.Message.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {

            }

        }
        binding.doesnothaveanaccount.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        super.onStart()
        }
}