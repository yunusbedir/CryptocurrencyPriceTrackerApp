package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coinhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentCoinHomeBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseFragment
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinHomeFragment : BaseFragment(),
    ListItemClickCallback<Coin>,
    SearchView.OnQueryTextListener,
    View.OnClickListener {

    private val coinViewModel: CoinViewModel by viewModels()

    private val coinListAdapter: CoinListAdapter = CoinListAdapter(this)

    private lateinit var binding: FragmentCoinHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.coinListRecyclerView.adapter = coinListAdapter
        binding.coinSearchView.setOnQueryTextListener(this)
        binding.logoutButton.setOnClickListener(this)
        initObserver()
        coinViewModel.filterCoins("")
    }

    private fun initObserver() {
        coinViewModel.coinListLiveData.observe(viewLifecycleOwner, EventObserver {
            coinListAdapter.submitList(it as MutableList<Coin>?)
        })
        coinViewModel.logoutLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it){
               /* val action = CoinHomeFragmentDirections.actionCoinHomeFragmentToUserAuthenticationGraph()
                findNavController().navigate(action)*/
            }
        })

        coinViewModel.screenStateLiveData.observe(viewLifecycleOwner, EventObserver {
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

    override fun onItemClick(item: Coin) {/*
        val action = CoinHomeFragmentDirections.actionCoinHomeFragmentToCoinDetailFragment(
            id = item.id,
            title = item.name ?: item.symbol ?: "Coin"
        )
        findNavController().navigate(action)*/
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            coinViewModel.filterCoins(it)
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.logoutButton -> {
                coinViewModel.logout()
            }
        }
    }
}