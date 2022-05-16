package com.yunusbedir.cryptocurrencypricetrackerapp.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.yunusbedir.cryptocurrencypricetrackerapp.MainActivity

open class BaseFragment : Fragment() {


    fun showProgressView() {
        if (activity != null) {
            when (activity) {
                is MainActivity -> {
                    (activity as MainActivity).binding.progressContainer.visibility =
                        View.VISIBLE
                }
            }
        }
    }

    fun dismissProgressView() {
        if (activity != null) {
            when (activity) {
                is MainActivity -> {
                    (activity as MainActivity).binding.progressContainer.visibility =
                        View.GONE
                }
            }
        }
    }
}