package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentRegisterBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainActivity
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.LoginViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.emailCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.passwordCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(),
    View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val registerFragmentViewModel: RegisterFragmentViewModel by viewModels()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(
            binding.loginButton,
            binding.registerButton
        ).forEach {
            it.setOnClickListener(this)
        }
        initObserver()
    }

    private fun initObserver() {
        registerFragmentViewModel.registerLiveData.observe(this, EventObserver { resource ->
            when (resource) {
                is Resource.LoadingResource -> {
                    loginViewModel.changeScreenState(ScreenState.ProgressState(true))
                }
                is Resource.SuccessResource -> {
                    loginViewModel.changeScreenState(ScreenState.ProgressState(false))
                    findNavController().navigateUp()
                }
                is Resource.FailedResource -> {
                    val message = resource.error.message ?: "Can not registered user!"
                    loginViewModel.changeScreenState(ScreenState.ToastMessageState(message))
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.loginButton -> {
                findNavController().navigateUp()
            }
            binding.registerButton -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                val password = binding.userPasswordTextInputEditText.text.toString()
                val confirmPassword = binding.userConfirmPasswordTextInputEditText.text.toString()
                if (password != confirmPassword) {
                    requireContext().showLongToast("Passwords are not the same")
                    return
                }
                if (requireContext().emailCheck(email) && requireContext().passwordCheck(password)) {
                    registerFragmentViewModel.registerUser(email, password)
                }
            }
        }
    }
}