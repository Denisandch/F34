package com.example.f34.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.f34.R
import com.example.f34.databinding.ActivityMainBinding
import com.example.f34.presentation.viewmodels.ApplicationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewmodel by viewModel<ApplicationViewModel>()

    private lateinit var navController: NavController
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        activityMainBinding.bottomNav.setupWithNavController(navController)

        initObserves()
    }

    private fun initObserves(){
        viewmodel.totalCountDevices.observe(this) {
            if (it == 0) activityMainBinding.bottomNav.removeBadge(R.id.cart)
            else activityMainBinding.bottomNav.getOrCreateBadge(R.id.cart).number = it
        }
    }
}
