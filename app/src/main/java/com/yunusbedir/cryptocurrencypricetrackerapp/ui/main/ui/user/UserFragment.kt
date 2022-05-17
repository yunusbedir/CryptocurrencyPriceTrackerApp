package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentUserBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment(),
    View.OnClickListener {

    private val userViewModel: UserViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()

        binding.signOutButton.setOnClickListener(this)
        userViewModel.getUser()
    }

    private fun initObserver() {
        userViewModel.userLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it != null) {
                binding.userEmailTextView.text = it.email
            } else {
                mainViewModel.signOutUser()
            }
        })
    }

    // region interfaces of views
    override fun onClick(v: View?) {
        when (v) {
            binding.signOutButton -> {
                mainViewModel.signOutUser()
            }
        }
    }
    // endregion
}