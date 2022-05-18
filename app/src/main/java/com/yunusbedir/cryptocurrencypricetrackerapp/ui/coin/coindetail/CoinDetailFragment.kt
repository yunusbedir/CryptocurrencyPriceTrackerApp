package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentCoinDetailBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.MainViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.EventObserver
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val coinDetailViewModel: CoinDetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val args by navArgs<CoinDetailFragmentArgs>()

    private val updateCurrentPriceInterval = 10000L
    private var updateCurrentPriceHandler: Handler? = null

    var updateCurrentPriceRunnable: Runnable = object : Runnable {
        override fun run() {
            try {
                coinDetailViewModel.updateCoinCurrentPrice()
            } finally {
                updateCurrentPriceHandler?.postDelayed(this, updateCurrentPriceInterval)
            }
        }
    }


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
        updateCurrentPriceHandler = Handler()
        updateCurrentPriceRunnable.run()
        mainViewModel.setToolbarVisibility(true)
        mainViewModel.changeScreenState(ScreenState.ProgressState(true))
        coinDetailViewModel.getCoinDetail(args.id)
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

            mainViewModel.changeScreenState(ScreenState.ProgressState(false))
        }
        coinDetailViewModel.isFavoriteLiveData.observe(viewLifecycleOwner) {
            binding.favoriteButton.background = if (it)
                requireContext().getDrawable(R.drawable.ic_favorite_enable)
            else
                requireContext().getDrawable(R.drawable.ic_favorite_disable)

        }
        coinDetailViewModel.currentPriceLiveData.observe(viewLifecycleOwner, EventObserver {
            binding.currentPriceTextView.text =
                "$${String.format("%.4f", it)}"
        })

    }

    override fun onDestroy() {
        updateCurrentPriceHandler?.removeCallbacks(updateCurrentPriceRunnable)
        super.onDestroy()
    }
}