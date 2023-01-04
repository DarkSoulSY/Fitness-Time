package com.example.fitnesstime.ui.home


import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityMainBinding


class Main : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var sharedPreferences: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.mystatusbar)
        setSupportActionBar(toolbar)

        val sharePreferences = getSharedPreferences("User Session", MODE_PRIVATE)
        val editor = sharePreferences.edit()


    }

    override fun onStart() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                /*R.id.welcomingFragment,
                R.id.signInFragment,
                R.id.signUpFragment,
                R.id.completeSignUp1Fragment,
                R.id.completeSignUp2Fragment,
                R.id.completeSignUp3Fragment,
                R.id.finishSignUpFragment,
                R.id.dashboardFragment,
                R.id.diaryFragment,
                R.id.progressFragment,
                R.id.bodyMassIndexFragment,
                R.id.settingsFragment,
                R.id.aboutFragment,
                R.id.nutritionFactsFragment*/
            )
        )
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)


        super.onStart()

    }




}