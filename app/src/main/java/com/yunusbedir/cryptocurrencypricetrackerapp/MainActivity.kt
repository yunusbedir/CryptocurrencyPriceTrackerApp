package com.yunusbedir.cryptocurrencypricetrackerapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        navController.addOnDestinationChangedListener { _, destination, args ->
            binding.toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            when (destination.id) {
                R.id.loginFragment -> {
                    title = getString(R.string.login)
                    binding.toolbar.visibility = View.GONE
                }
                R.id.registerFragment -> {
                    title = getString(R.string.register_now)
                    binding.toolbar.visibility = View.VISIBLE
                }
                R.id.forgotPasswordFragment -> {
                    title = getString(R.string.forget_password)
                    binding.toolbar.visibility = View.VISIBLE
                }
                R.id.coinHomeFragment -> {
                    binding.toolbar.visibility = View.GONE
                }
                R.id.coinDetailFragment -> {
                    title = args?.getString("title")
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
        }
    }
}