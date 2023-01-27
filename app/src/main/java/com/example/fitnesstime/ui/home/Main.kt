package com.example.fitnesstime.ui.home


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.fitnesstime.R
import com.example.fitnesstime.databinding.ActivityMainBinding
import com.example.fitnesstime.network.NetworkConnection


class Main : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var sharedPreferences: SharedPreferences


    private lateinit var networkConnection: NetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.mystatusbar)
        setSupportActionBar(toolbar)
        callNetworkConnection()

        sharedPreferences = getSharedPreferences("User Session", MODE_PRIVATE)


        if(sharedPreferences.getBoolean("MODE", false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onStart() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView1) as NavHostFragment
        navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration(

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
            navController.graph

        )
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        super.onStart()
    }

    fun signOut() {
        Intent(this, Main::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }.let { startActivity(it) }
        finish()
    }

    private fun callNetworkConnection(){
        networkConnection = NetworkConnection(application)
        networkConnection.observe(this) { isConnected ->
            binding.apply {
                Connected.isGone = !isConnected
                Disconnected.isGone = isConnected
            }

        }

    }
}
