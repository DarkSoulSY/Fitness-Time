package com.example.fitnesstime.ui.main.fragments

import android.R
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.fitnesstime.databinding.FragmentSettingsBinding
import com.example.fitnesstime.ui.profile.Profile
import kotlinx.android.synthetic.*


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.bo7meedidiotic.setOnClickListener {
            val intent = Intent(this@SettingsFragment.requireContext(), Profile::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view




    }

}