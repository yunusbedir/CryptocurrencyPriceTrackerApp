package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ItemViewCoinListBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ItemViewFavoriteListBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.util.GenericDiffUtil
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImage

class FavoriteListAdapter(private val listItemCLickCallback: ListItemClickCallback<CoinDetail>) :
    ListAdapter<CoinDetail, FavoriteListAdapter.FavoriteListViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        return FavoriteListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            listItemCLickCallback
        )
    }

    class FavoriteListViewHolder(private val binding: ItemViewFavoriteListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: CoinDetail,
            listItemCLickCallback: ListItemClickCallback<CoinDetail>
        ) {
            binding.marketRankTextView.text = item.marketCapRank
            binding.symbolTextView.text = item.symbol
            item.image?.url?.let { binding.iconImageView.loadImage(it) }
            binding.last24hTextView.text =
                "${item.marketData?.priceChangePercentage24h.toString()}%"
            binding.currentPriceTextView.text = "$${item.marketData?.currentPrice?.usd.toString()}"

            binding.root.setOnClickListener {
                listItemCLickCallback.onItemClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): FavoriteListViewHolder {
                val binding = ItemViewFavoriteListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return FavoriteListViewHolder(binding)
            }
        }
    }

}