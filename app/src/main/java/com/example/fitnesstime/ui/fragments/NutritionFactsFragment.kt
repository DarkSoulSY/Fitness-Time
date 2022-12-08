package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentNutritionFactsBinding
import com.example.fitnesstime.databinding.FragmentProgressBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class NutritionFactsFragment : Fragment() {

    private lateinit var binding: FragmentNutritionFactsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNutritionFactsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true

        binding.showHideFacts.setOnClickListener {
            showHide()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showHide() {
        if (binding.showHideFacts.text == "Show") {
            binding.nutritionFactsTable.isVisible = true
            binding.showHideFacts.text = "Hide"
        }
        else {
            binding.nutritionFactsTable.isVisible = false
            binding.showHideFacts.text = "Show"
        }


    }
}