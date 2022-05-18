package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentLoginBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.LoginViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.emailCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.passwordCheck
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(),
    View.OnClickListener {

    private val loginViewModel: LoginViewModel by activityViewModels()

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
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.forgotPasswordTextView -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                val action =
                    LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(email)
                findNavController().navigate(action)
            }
            binding.loginButton -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                val password = binding.userPasswordTextInputEditText.text.toString()

                if (requireContext().emailCheck(email) && requireContext().passwordCheck(password)) {
                    loginViewModel.changeScreenState(ScreenState.ProgressState(true))
                    loginViewModel.loginUser(email, password)
                }
            }
            binding.registerButton -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment(email)
                findNavController().navigate(action)
            }
        }
    }
}