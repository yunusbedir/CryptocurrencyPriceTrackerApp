package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentLoginBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseFragment
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.UserAuthenticationViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.emailCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.passwordCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(),
    View.OnClickListener {

    private val userAuthenticationViewModel: UserAuthenticationViewModel by hiltNavGraphViewModels(
        navGraphId = R.id.user_authentication_graph
    )
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(
            binding.forgotPasswordTextView,
            binding.loginButton,
            binding.registerButton
        ).forEach {
            it.setOnClickListener(this)
        }
        initObservers()
    }

    private fun initObservers() {
        userAuthenticationViewModel.loginLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                findNavController().navigate(R.id.action_global_coinHomeFragment)
            }
        })
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
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.forgotPasswordTextView -> {
                var email = binding.userEmailTextInputEditText.text.toString()
                if (requireContext().emailCheck(email).not())
                    email = ""
                val action =
                    LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(email)
                findNavController().navigate(action)
            }
            binding.loginButton -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                val password = binding.userPasswordTextInputEditText.text.toString()

                if (requireContext().emailCheck(email) && requireContext().passwordCheck(password)) {
                    userAuthenticationViewModel.loginUser(email, password)
                }
            }
            binding.registerButton -> {
                var email = binding.userEmailTextInputEditText.text.toString()
                if (requireContext().emailCheck(email).not())
                    email = ""
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment(email)
                findNavController().navigate(action)
            }
        }
    }
}