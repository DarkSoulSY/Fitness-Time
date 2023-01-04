package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentSignUpBinding
import com.example.fitnesstime.ui.model.viewmodel.UserSignUpInformationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.fitnesstime.validation.Validation


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val sharedViewModel: UserSignUpInformationViewModel by activityViewModels()

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
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        binding.apply {

            //Moving to Sign in Page in case he has an account
            alreadyhaveanaccount.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }

            signup1Next.setOnClickListener {
                if
                    (Validation.validateInput(signupFirstName, requireActivity())&&
                    Validation.validateInput(signupLastName, requireActivity())&&
                    Validation.validateInput(signupEmail, requireActivity())&&
                    Validation.validateInput(signupPhoneNumber, requireActivity())&&
                    Validation.validateInput(signupPassword, requireActivity())) {

                    if (!Patterns.EMAIL_ADDRESS.matcher(signupEmail.text).matches())
                        Toast.makeText(activity, "${signupEmail.hint} is not written in the correct way!", Toast.LENGTH_SHORT).show()

                    else{
                        //If the passwords are matching
                        if (signupPassword.text.toString() == signupConfirmPassword.text.toString()){
                            //Fill User View Model with Data
                            sharedViewModel.apply {
                                setFirstName(signupFirstName.text.toString().toUpperCase())
                                setLastName(signupLastName.text.toString().toUpperCase())
                                setEmail(signupEmail.text.toString().toLowerCase())
                                setPhoneNumber(signupPhoneNumber.text.toString())
                                setPassword(signupPassword.text.toString())
                            }

                            //Move to next fragment
                            findNavController().navigate(R.id.action_signUpFragment_to_completeSignUp1Fragment)
                        }
                        else
                            Toast.makeText(activity, "passwords mismatch!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        super.onStart()
    }
}