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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.FragmentProfileBinding
import com.example.fitnesstime.ui.home.Main
import com.example.fitnesstime.ui.viewmodel.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val personalViewModel: ProfileViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        personalViewModel.profileEmail.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty()) {
                personalViewModel.apply {
                    binding.profileFirstName.text = profileFirstName.value
                    binding.profileLastName.text = profileLastName.value
                    binding.profileEmail.text = profileEmail.value
                }

            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = requireActivity().getSharedPreferences("User Session", MODE_PRIVATE)
        val email = sharedPreferences.getString("Email", null)
        if (!email.isNullOrEmpty())
            try {
                personalViewModel.setProfile(email)
            } catch (e: Exception) {

            }


        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)!!.isGone = true

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        binding.profileLogOut.setOnClickListener {
            showLogOutDialog()
        }
    }

    private fun showLogOutDialog(){
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setNegativeButton("No") { dialog, _ -> dialog.cancel()}
            .setPositiveButton("Yes") { _, _ ->
                sharedPreferences.edit().clear().apply()
                (requireActivity() as Main).signOut()
            }
            .show()
    }

}