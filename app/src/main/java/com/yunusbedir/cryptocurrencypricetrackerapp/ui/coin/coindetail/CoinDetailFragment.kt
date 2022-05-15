package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentCoinDetailBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.FragmentCoinHomeBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private val coinDetailViewModel: CoinDetailViewModel by viewModels()

    private val args by navArgs<CoinDetailFragmentArgs>()

    private lateinit var binding: FragmentCoinDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        coinDetailViewModel.getCoinDetail(args.id)
    }

    @SuppressLint("SetTextI18n")
    private fun initObserver() {
        coinDetailViewModel.coinDetailLiveData.observe(viewLifecycleOwner) { coinDetail ->
                binding.descriptionTextView.text = coinDetail.description?.en ?: ""
                coinDetail.image?.url?.let { it1 -> binding.coinIconImageView.loadImage(it1) }
                binding.symbolTextView.text = coinDetail.symbol
                binding.currentPriceTextView.text = "$${String.format("%.4f", coinDetail.marketData?.currentPrice?.usd ?: "")}"
                binding.hashingTextView.text = coinDetail.hashing
                binding.priceChangePercentageTextView.text = String.format("%.2f", coinDetail.marketData?.priceChangePercentage24h) + " %"
        }
    }

}