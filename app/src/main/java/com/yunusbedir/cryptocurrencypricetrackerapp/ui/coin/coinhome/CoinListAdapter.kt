package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coinhome

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yunusbedir.cryptocurrencypricetrackerapp.callback.ListItemClickCallback
import com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.databinding.ItemViewCoinListBinding
import com.yunusbedir.cryptocurrencypricetrackerapp.util.GenericDiffUtil
import com.yunusbedir.cryptocurrencypricetrackerapp.util.loadImage

class CoinListAdapter(private val listItemCLickCallback: ListItemClickCallback<Coin>) :
    ListAdapter<Coin, CoinListAdapter.CoinListViewHolder>(GenericDiffUtil()),
    Filterable {

    var originalList: MutableList<Coin>?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        return CoinListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bind(
            getItem(position),
            listItemCLickCallback
        )
    }

    override fun submitList(list: MutableList<Coin>?) {
        submitList(list,false)
    }

    private fun submitList(list: MutableList<Coin>?, filtered: Boolean) {
        if (!filtered)
            originalList = list
        super.submitList(list)
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = if (constraint.isNullOrEmpty())
                        originalList
                    else {
                        originalList?.filter {
                            it.symbol?.contains(constraint) ?: false || it.name?.contains(constraint) ?: false
                        }
                    }
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as MutableList<Coin>?, true)
            }
        }
    }
}