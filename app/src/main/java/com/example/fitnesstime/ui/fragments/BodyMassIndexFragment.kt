package com.example.fitnesstime.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentBodyMassIndexBinding
import com.example.fitnesstime.enum.Gender
import com.example.fitnesstime.ui.viewmodel.BodyMassIndexViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BodyMassIndexFragment : Fragment() {

    private lateinit var binding: FragmentBodyMassIndexBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val personalViewModel: BodyMassIndexViewModel by activityViewModels()


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBodyMassIndexBinding.inflate(inflater, container, false)
        sharedPreferences =
            requireActivity().getSharedPreferences("User Session", Context.MODE_PRIVATE)

        val df = DecimalFormat("#.#")
        val observer = Observer<Gender> {
            if (it != null) {
                if (it == Gender.Male) {
                    binding.linearLayout2.isGone = false
                    binding.linearLayout3.isGone = true
                }
                else {
                    binding.linearLayout2.isGone = true
                    binding.linearLayout3.isGone = false
                }
                df.roundingMode = RoundingMode.DOWN
                try {
                    GlobalScope.launch(Dispatchers.Main) {
                        val end = personalViewModel.calculateBMI()
                        var i = 0.0
                        while (i <= end) {
                            //value animator
                            delay(1L)
                            binding.bodyMassIndexBmi.text = df.format(i).toString()
                            i += 0.1
                        }
                    }
                } catch (e: Exception) {

                }

            }
        }
        personalViewModel.gender.observe(requireActivity(), observer)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isVisible = true
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        val email = sharedPreferences.getString("Email", null)
        try {
            personalViewModel.setBodyMassIndexInformation(requireActivity(), email!!)
        } catch (e: Exception) {

        }

    }

}