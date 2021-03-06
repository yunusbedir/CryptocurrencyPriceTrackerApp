package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentFavoriteBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail.CoinDetailFragmentDirections
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.markets.CoinListAdapter
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(),
    ListItemClickCallback<CoinDetail> {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val favoriteListAdapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter(this)
    }


    private lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setToolbarVisibility(false)
        binding.favoriteListRecyclerView.adapter = favoriteListAdapter

        initObserver()

        favoriteViewModel.getMyFavoriteCoins()
    }

    private fun initObserver() {
        favoriteViewModel.favoriteListLiveData.observe(viewLifecycleOwner, EventObserver { resource ->
            when (resource) {
                is Resource.LoadingResource -> {
                    mainViewModel.changeScreenState(ScreenState.ProgressState(true))
                }
                is Resource.SuccessResource -> {
                    mainViewModel.changeScreenState(ScreenState.ProgressState(false))
                    favoriteListAdapter.submitList(resource.data)
                }
                is Resource.FailedResource -> {
                    val message = resource.error.message ?: "Can not found  Favorite Coin list!"
                    mainViewModel.changeScreenState(ScreenState.ToastMessageState(message))
                }
            }
        })
    }

    override fun onItemClick(item: CoinDetail) {
        val action =
            CoinDetailFragmentDirections.actionGlobalCoinDetailFragment(item.id, item.symbol)
        findNavController().navigate(action)
    }

}