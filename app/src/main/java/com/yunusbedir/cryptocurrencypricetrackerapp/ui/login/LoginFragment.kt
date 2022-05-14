package com.yunusbedir.cryptocurrencypricetrackerapp.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentLoginBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.util.emailCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.passwordCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(),
    View.OnClickListener {

    private val loginFragmentViewModel: LoginFragmentViewModel by viewModels()

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
        loginFragmentViewModel.loginLiveData.observe(viewLifecycleOwner){

        }
        loginFragmentViewModel.screenStateLiveData.observe(viewLifecycleOwner){
            when(it){
                is ScreenState.ProgressState ->{
                    if (it.visibility){

                    }else{

                    }
                }
                is ScreenState.ToastMessageState -> {
                    requireContext().showLongToast(it.message)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.forgotPasswordTextView -> {
                // TODO: Navigate To ForgotPasswordFragment
            }
            binding.loginButton -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                val password = binding.userPasswordTextInputEditText.text.toString()

                if (requireContext().emailCheck(email) && requireContext().passwordCheck(password)) {
                    loginFragmentViewModel.loginUser(email, password)
                }
            }
            binding.registerButton -> {
                // TODO: Navigate To RegisterFragment
            }
        }
    }
}