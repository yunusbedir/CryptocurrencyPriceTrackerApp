package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentForgotPasswordBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.UserAuthenticationViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.emailCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.passwordCheck
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment(),
    View.OnClickListener {

    private val userAuthenticationViewModel: UserAuthenticationViewModel by hiltNavGraphViewModels(
        navGraphId = R.id.user_authentication_graph
    )

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
        initObservers()
    }

    private fun initObservers() {
        userAuthenticationViewModel.forgotPasswordLivedata.observe(viewLifecycleOwner, EventObserver{
            if (it){
                findNavController().navigateUp()
            }
        })
        userAuthenticationViewModel.screenStateLiveData.observe(viewLifecycleOwner, EventObserver{
            when (it) {
                is ScreenState.ProgressState -> {
                    if (it.visibility) {

                    } else {

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
            binding.sendMailButton -> {
                val email = binding.userEmailTextInputEditText.text.toString()
                if (requireContext().emailCheck(email)) {
                    userAuthenticationViewModel.forgotPassword(email)
                }
            }
            binding.loginButton -> {
                findNavController().navigateUp()
            }
        }
    }
}