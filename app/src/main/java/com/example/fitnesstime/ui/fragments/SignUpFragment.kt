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
import com.example.fitnesstime.ui.viewmodel.UserSignUpInformationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.fitnesstime.utilities.Validator


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val sharedViewModel: UserSignUpInformationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            haveAnAccount.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }

            signup1Next.setOnClickListener {
                if
                    (Validator.validateInput(signupFirstName, requireActivity())&& Validator.validateInput(signupLastName, requireActivity())&&
                    Validator.validateInput(signupEmail, requireActivity())&& Validator.validateInput(signupPhoneNumber, requireActivity())&&
                    Validator.isValidPassword(signupPassword.text.toString(), binding.root)) {

                    if (!Patterns.EMAIL_ADDRESS.matcher(signupEmail.text.toString()).matches())
                        Toast.makeText(activity, "${signupEmail.hint} email should follow: \n username@domain.domain \n Structure!", Toast.LENGTH_SHORT).show()
                    else
                        //If the passwords are matching
                        if (signupPassword.text.toString() == signupConfirmPassword.text.toString()){
                            //Fill User View Model with Data
                            sharedViewModel.apply {
                                setFirstName(signupFirstName.text.toString().uppercase())
                                setLastName(signupLastName.text.toString().uppercase())
                                setEmail(signupEmail.text.toString().lowercase())
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
        super.onStart()
    }
}