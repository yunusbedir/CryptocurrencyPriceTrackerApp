package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentRegisterBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseFragment
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.UserAuthenticationViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.emailCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.passwordCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment(),
    View.OnClickListener {

    private val userAuthenticationViewModel: UserAuthenticationViewModel by hiltNavGraphViewModels(
        navGraphId = R.id.user_authentication_graph
    )

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
        initObservers()
    }

    private fun initObservers() {
        userAuthenticationViewModel.screenStateLiveData.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is ScreenState.ProgressState -> {
                    if (it.visibility) {
                        showProgressView()
                    } else {
                        dismissProgressView()
                    }
                }
                is ScreenState.ToastMessageState -> {
                    requireContext().showLongToast(it.message)
                }
            }
        })
        userAuthenticationViewModel.registerLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it){
                findNavController().navigateUp()
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
                    userAuthenticationViewModel.registerUser(email, password)
                }
            }
        }
    }
}