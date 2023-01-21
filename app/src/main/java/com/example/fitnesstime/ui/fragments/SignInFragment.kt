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
        }
        binding.doesnothaveanaccount.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        super.onStart()
        }
    /*private fun inquireAboutUser(user: LoginAccountDTO){
        //Preparing Params
        val request = LoginAccountDTO(email = user.email, password = user.password)
        suspend {
        val call = RetrofitInstance.retrofit.signInAccount(request.email, request.password)
        call.enqueue(object : Callback<ServiceResponse<LoginAccountDTO>> {
            override fun onResponse(call: Call<ServiceResponse<LoginAccountDTO>>, response: Response<ServiceResponse<LoginAccountDTO>>) {
                response.body()?.let {
                    if (response.isSuccessful) {

                        Toast.makeText(context, response.body()!!.data!!.email + response.body()!!.Message, Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_signInFragment_to_dashboardFragment)
                    }
                    else
                        Toast.makeText(context, response.body()!!.Success.toString() + response.body()!!.Message as String, Toast.LENGTH_LONG).show()

                }


            }
            override fun onFailure(call: Call<ServiceResponse<LoginAccountDTO>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })}

    }*/

}