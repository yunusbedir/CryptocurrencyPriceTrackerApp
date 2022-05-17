package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.markets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ItemViewCoinListBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.util.GenericDiffUtil

class CoinListAdapter(private val listItemCLickCallback: ListItemClickCallback<Coin>) :
    ListAdapter<Coin, CoinListAdapter.CoinListViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        return CoinListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            listItemCLickCallback
        )
    }

    class CoinListViewHolder(private val binding: ItemViewCoinListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Coin,
            listItemCLickCallback: ListItemClickCallback<Coin>
        ) {
            binding.symbolTextView.text = item.symbol
            binding.nameTextView.text = item.name

            binding.root.setOnClickListener {
                listItemCLickCallback.onItemClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CoinListViewHolder {
                val binding = ItemViewCoinListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CoinListViewHolder(binding)
            }
        }
    }

}