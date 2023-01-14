package com.example.fitnesstime.ui.home


import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.connection.RetrofitInstance
import com.example.fitnesstime.databinding.ActivityMainBinding
import com.example.fitnesstime.ui.fragments.DashboardFragment
import com.example.fitnesstime.ui.fragments.WelcomingFragment
import com.example.fitnesstime.ui.repositories.UserAccountInformationRepository
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.jar.Manifest


class Main : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var sharedPreferences: SharedPreferences
    private var userRepo = UserAccountInformationRepository(RetrofitInstance.retrofit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.mystatusbar)
        setSupportActionBar(toolbar)

        sharedPreferences = getSharedPreferences("User Session", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
    }

    override fun onStart() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
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
