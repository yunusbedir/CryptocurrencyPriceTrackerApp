package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ActivityLoginBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainActivity
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_activity_login)
    }

    private val viewModel: UserAuthenticationViewModel by viewModels()

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initObservers()
        viewModel.autoLogin()
    }

    private fun initObservers() {
        viewModel.loginLiveData.observe(this, EventObserver {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
        viewModel.registerLiveData.observe(this, EventObserver {
            if (it) {
                navController.navigateUp()
            }
        })
        viewModel.forgotPasswordLivedata.observe(this, EventObserver {
            if (it) {
                navController.navigateUp()
            }
        })
        viewModel.screenStateLiveData.observe(this, EventObserver {
            when (it) {
                is ScreenState.ProgressState -> {
                    if (it.visibility) {
                        binding.progressContainer.visibility =
                            View.VISIBLE
                    } else {
                        binding.progressContainer.visibility =
                            View.GONE
                    }
                }
                is ScreenState.ToastMessageState -> {
                    showLongToast(it.message)
                }
            }
        })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        navController.addOnDestinationChangedListener { _, destination, args ->
            binding.toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            binding.progressContainer.visibility = View.GONE
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
            }
        }
    }
}