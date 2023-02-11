package com.example.fitnesstime.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fitnesstime.R
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.FragmentProfileBinding
import com.example.fitnesstime.ui.home.Main
import com.example.fitnesstime.ui.model.AddDailyWeight
import com.example.fitnesstime.ui.model.ChangePassword
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import com.example.fitnesstime.ui.viewmodel.ProfileViewModel
import com.example.fitnesstime.utilities.Validator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val personalViewModel: ProfileViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private val api = RetrofitInstance.retrofit
    private val userRepo = UserAccountInformationRepository(api)

    private lateinit var oldPassword: EditText
    private lateinit var newPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var change: Button
    private lateinit var cancel: Button

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
                    binding.profileFirstNameEdit.text =
                        SpannableStringBuilder(profileFirstName.value)
                    binding.profileLastNameEdit.text = SpannableStringBuilder(profileLastName.value)


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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        binding.profileLogOut.setOnClickListener {
            showLogOutDialog()
        }

        binding.apply {
            profileChangePasswordUser.setOnClickListener {
                buildPasswordDialog()
            }

            profileEditProfileButton.setOnClickListener {
                if (profileEditProfileButton.text.toString() == "Edit Profile") {
                    profileEditProfileButton.text = "Done"
                    profileFirstName.isGone = true
                    profileLastName.isGone = true
                    profileFirstNameEdit.isGone = false
                    profileLastNameEdit.isGone = false
                } else if (profileEditProfileButton.text.toString() == "Done") {
                    val email = sharedPreferences.getString("Email", null)
                    val password = sharedPreferences.getString("Password", null)

                    var firstName: String? = binding.profileFirstNameEdit.text.toString()
                    var lastName: String? = binding.profileLastNameEdit.text.toString()

                    if (firstName == "")
                        firstName = null
                    if (lastName == "")
                        lastName = null
                    if (!(firstName == binding.profileFirstName.text.toString() && lastName == profileLastName.text.toString()))
                        GlobalScope.launch(Dispatchers.IO) {
                            val response = userRepo.editProfile(email!!, password!!, firstName, lastName)
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful)
                                    if (response.body()!!.Success) {
                                        Snackbar.make(
                                            requireView(),
                                            "Changed profile parameters successfully!",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                        personalViewModel.setProfile(email)

                                    } else
                                        Snackbar.make(
                                            requireView(),
                                            " Could not change profile parameters successfully!",
                                            Snackbar.LENGTH_SHORT
                                        ).show()


                            }
                        }
                    profileEditProfileButton.text = "Edit Profile"
                    profileFirstName.isGone = false
                    profileLastName.isGone = false
                    profileFirstNameEdit.isGone = true
                    profileLastNameEdit.isGone = true
                }

            }
        }
    }

    private fun showLogOutDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Yes") { _, _ ->
                sharedPreferences.edit().clear().apply()
                (requireActivity() as Main).signOut()
            }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildPasswordDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val contactPopupView: View = layoutInflater.inflate(R.layout.change_password, null)
        oldPassword = contactPopupView.findViewById(R.id.profile_change_old_password)
        newPassword = contactPopupView.findViewById(R.id.profile_change_new_password)
        confirmPassword = contactPopupView.findViewById(R.id.profile_change_confirm_password)
        change = contactPopupView.findViewById(R.id.profile_change_password_btn1)
        cancel = contactPopupView.findViewById(R.id.profile_change_password_btn2)

        dialogBuilder.setView(contactPopupView)
        dialogBuilder.create()
            .setTitle("Add your current weight.")
        val alertDialog = dialogBuilder.show()


        change.setOnClickListener {
            val email = sharedPreferences.getString("Email", null)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            if (newPassword.text.toString() == confirmPassword.text.toString()) {
                if (!email.isNullOrBlank() && Validator.isValidPassword(
                        newPassword.text.toString(),
                        contactPopupView
                    )
                ) {
                    try {
                        GlobalScope.launch(Dispatchers.IO) {
                            val response = userRepo.changePassword(
                                ChangePassword(
                                    email = email,
                                    password = oldPassword.text.toString(),
                                    new_password = newPassword.text.toString()
                                )
                            )
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    response.body()!!.apply {
                                        if (Success) {
                                            Snackbar.make(
                                                contactPopupView,
                                                Message!!,
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                            alertDialog?.dismiss()
                                        } else
                                            Snackbar.make(
                                                contactPopupView,
                                                Message!!,
                                                Snackbar.LENGTH_SHORT
                                            ).show()

                                    }

                                }
                            }
                        }
                    } catch (e: Exception) {

                    }
                }

            }
            else
                Snackbar.make(contactPopupView, "New password and confirm password fields must match!", Snackbar.LENGTH_SHORT).show()
            }

        cancel.setOnClickListener {
            alertDialog?.dismiss()
        }

    }

}