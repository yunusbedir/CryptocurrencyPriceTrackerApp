package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coinhome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentCoinHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinHomeFragment : Fragment(),
    ListItemClickCallback<Coin>,
    SearchView.OnQueryTextListener {

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
        initObserver()
        coinViewModel.syncCoins()
    }

    private fun initObserver() {
        coinViewModel.coinListLiveData.observe(viewLifecycleOwner) {
            coinListAdapter.submitList(it as MutableList<Coin>?)
        }
    }

    override fun onItemClick(item: Coin) {
        //TODO : Navigate To CoinDetailFragment
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
}