package com.yunusbedir.cryptocurrencypricetrackerapp.util

import androidx.recyclerview.widget.DiffUtil
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin

class GenericDiffUtil<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is Coin -> {
                oldItem as Coin == newItem
            }
            else -> throw Exception("Not supported")
        }
    }
}