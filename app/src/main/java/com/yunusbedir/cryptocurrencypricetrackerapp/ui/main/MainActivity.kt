package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ActivityMainBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.UserAuthenticationViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    private val viewModel: MainViewModel by viewModels()


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initNavigationView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.toolbarVisibilityLiveData.observe(this, EventObserver {
            binding.toolbar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun initNavigationView() {
        val navView: BottomNavigationView = binding.navView
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_markets, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }
}