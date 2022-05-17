package com.yunusbedir.cryptocurrencypricetrackerapp.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.LoginActivity

open class BaseFragment : Fragment() {


    fun showProgressView() {
        if (activity != null) {
            when (activity) {
                is LoginActivity -> {
                    (activity as LoginActivity).binding.progressContainer.visibility =
                        View.VISIBLE
                }
            }
        }
    }

    fun dismissProgressView() {
        if (activity != null) {
            when (activity) {
                is LoginActivity -> {
                    (activity as LoginActivity).binding.progressContainer.visibility =
                        View.GONE
                }
            }
        }
    }
}