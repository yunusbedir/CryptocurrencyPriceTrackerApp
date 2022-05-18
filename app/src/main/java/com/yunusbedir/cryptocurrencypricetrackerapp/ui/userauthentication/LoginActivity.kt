package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ActivityLoginBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainActivity
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImage
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImageWithResource
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_activity_login)
    }

    private val viewModel: LoginViewModel by viewModels()

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initToolbar()
        initObservers()
        viewModel.changeScreenState(ScreenState.ProgressState(true))
        viewModel.checkSignedIn()
    }

    private fun initViews() {
        binding.loadingGifImageView.loadImageWithResource(R.raw.loading_gif)
    }

    private fun initObservers() {
        viewModel.loginLiveData.observe(this, EventObserver {
            if (it) {
                viewModel.changeScreenState(ScreenState.ProgressState(false))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        viewModel.screenStateLiveData.observe(this, EventObserver {
            when (it) {
                is ScreenState.ProgressState -> {
                    binding.progressContainer.visibility =
                        if (it.visibility) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                }
                is ScreenState.ToastMessageState -> {
                    binding.progressContainer.visibility = View.GONE
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