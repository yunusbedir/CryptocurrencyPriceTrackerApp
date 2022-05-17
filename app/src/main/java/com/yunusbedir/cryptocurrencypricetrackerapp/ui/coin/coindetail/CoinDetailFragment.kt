package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentCoinDetailBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseFragment
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImage
import com.yunusbedir.cryptocurrencypricetrackerapp.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : BaseFragment() {

    private val coinDetailViewModel: CoinDetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val args by navArgs<CoinDetailFragmentArgs>()

    private lateinit var binding: FragmentCoinDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinDetailBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        coinDetailViewModel.getCoinDetail(args.id)
        mainViewModel.setToolbarVisibility(true)
        binding.favoriteButton.setOnClickListener {
            coinDetailViewModel.toggleFavorite()
        }
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun initObserver() {
        coinDetailViewModel.coinDetailLiveData.observe(viewLifecycleOwner) { coinDetail ->
            binding.descriptionTextView.text = coinDetail.description?.en ?: ""
            coinDetail.image?.url?.let { it1 -> binding.coinIconImageView.loadImage(it1) }
            binding.symbolTextView.text = coinDetail.symbol
            binding.currentPriceTextView.text =
                "$${String.format("%.4f", coinDetail.marketData?.currentPrice?.usd ?: "")}"
            binding.hashingTextView.text = coinDetail.hashing
            binding.priceChangePercentageTextView.text =
                String.format("%.2f", coinDetail.marketData?.priceChangePercentage24h) + " %"
        }
        coinDetailViewModel.isFavoriteLiveData.observe(viewLifecycleOwner) {
            binding.favoriteButton.background = if (it)
                requireContext().getDrawable(R.drawable.ic_favorite_enable)
            else
                requireContext().getDrawable(R.drawable.ic_favorite_disable)

        }

        coinDetailViewModel.screenStateLiveData.observe(viewLifecycleOwner, EventObserver {
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
}