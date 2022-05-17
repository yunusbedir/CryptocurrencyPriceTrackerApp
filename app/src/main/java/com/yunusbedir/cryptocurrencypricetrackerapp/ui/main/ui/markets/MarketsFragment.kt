package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.markets

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentMarketsBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail.CoinDetailFragmentDirections
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketsFragment : Fragment(),
    SearchView.OnQueryTextListener,
    ListItemClickCallback<Coin> {

    private val marketsViewModel: MarketsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    private val coinListAdapter: CoinListAdapter by lazy {
        CoinListAdapter(this)
    }

    private lateinit var binding: FragmentMarketsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.coinListRecyclerView.adapter = coinListAdapter
        binding.coinSearchView.setOnQueryTextListener(this)
        mainViewModel.setToolbarVisibility(false)
        initObserver()
        marketsViewModel.filterCoins("")
    }

    private fun initObserver() {
        marketsViewModel.coinListLiveData.observe(viewLifecycleOwner, EventObserver {
            coinListAdapter.submitList(it as MutableList<Coin>?)
        })

        marketsViewModel.screenStateLiveData.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is ScreenState.ProgressState -> {
                    if (it.visibility) {
                        //showProgressView()
                    } else {
                        //dismissProgressView()
                    }
                }
                is ScreenState.ToastMessageState -> {
                    requireContext().showLongToast(it.message)
                }
            }
        })
    }

    override fun onItemClick(item: Coin) {
        //Navigation Coin Detail
        val action =
            CoinDetailFragmentDirections.actionGlobalCoinDetailFragment(item.id, item.symbol.toString())
        findNavController().navigate(action)
        requireContext().showLongToast("${item.symbol} is clicked")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            marketsViewModel.filterCoins(it)
        }
        return false
    }
}