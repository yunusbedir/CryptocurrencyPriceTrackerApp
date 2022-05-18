package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ActivityMainBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.LoginActivity
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImageWithResource
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
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
        initViews()
        initNavigationView()
        initObserver()
    }

    private fun initViews() {
        binding.loadingGifImageView.loadImageWithResource(R.raw.loading_gif)
    }

    private fun initObserver() {
        viewModel.toolbarVisibilityLiveData.observe(this, EventObserver {
            binding.toolbar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.signOutLiveData.observe(this, EventObserver {
            viewModel.changeScreenState(ScreenState.ProgressState(false))
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        viewModel.screenStateLiveData.observe(this, EventObserver {
            when (it) {
                is ScreenState.ProgressState -> {
                    binding.progressContainer.visibility = when (it.visibility) {
                        true -> View.VISIBLE
                        else -> View.GONE
                    }
                }
                is ScreenState.ToastMessageState -> {
                    binding.progressContainer.visibility = View.GONE
                    showLongToast(it.message)
                }
            }
        })
    }

    private fun initNavigationView() {
        val navView: BottomNavigationView = binding.navView
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_markets, R.id.navigation_favorite, R.id.navigation_user))
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