package com.example.fitnesstime.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitnesstime.databinding.FragmentEditMacrosAndCaloriesBinding

class EditMacrosAndCaloriesFragment : Fragment() {

    private lateinit var binding: FragmentEditMacrosAndCaloriesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditMacrosAndCaloriesBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onStart() {

        binding.apply {


        }
        super.onStart()
    }

}