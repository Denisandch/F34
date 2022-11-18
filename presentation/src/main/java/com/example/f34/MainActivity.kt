package com.example.f34

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.f34.databinding.ActivityMainBinding
import com.example.f34.viewmodels.ApplicationViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: ApplicationViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        activityMainBinding.bottomNav.setupWithNavController(navController)

        viewModel.totalCountDevices.observe(this) {
            if (it == 0) activityMainBinding.bottomNav.removeBadge(R.id.cart)
            else activityMainBinding.bottomNav.getOrCreateBadge(R.id.cart).number = it
        }
    }
}
