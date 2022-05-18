package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentForgotPasswordBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.LoginViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.emailCheck
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment(),
    View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val forgotPasswordFragmentViewModel: ForgotPasswordFragmentViewModel by viewModels()

    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(
            binding.sendMailButton,
            binding.loginButton
        ).forEach {
            it.setOnClickListener(this)
        }
        initObserver()
    }

    private fun initObserver() {
        forgotPasswordFragmentViewModel.forgotPasswordLivedata.observe(this, EventObserver { resource ->
            when (resource) {
                is Resource.LoadingResource -> {
                    loginViewModel.changeScreenState(ScreenState.ProgressState(true))
                }
                is Resource.SuccessResource -> {
                    loginViewModel.changeScreenState(ScreenState.ProgressState(false))
                    findNavController().navigateUp()
                }
                is Resource.FailedResource -> {
                    val message = resource.error.message ?: "Failed to send password reset email!"
                    loginViewModel.changeScreenState(ScreenState.ToastMessageState(message))
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.sendMailButton -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                if (requireContext().emailCheck(email)) {
                    loginViewModel.changeScreenState(ScreenState.ProgressState(true))
                    forgotPasswordFragmentViewModel.forgotPassword(email)
                }
            }
            binding.loginButton -> {
                findNavController().navigateUp()
            }
        }
    }
}