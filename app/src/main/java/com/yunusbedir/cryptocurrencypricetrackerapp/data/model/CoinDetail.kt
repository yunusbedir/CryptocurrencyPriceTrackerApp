package com.yunusbedir.cryptocurrencypricetrackerapp.data.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.annotations.SerializedName


data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("hashing_algorithm")
    val hashing: String?,
    @SerializedName("market_data")
    val marketData: MarketData?,
    val description: Description?,
    val image: Image?,
    var isFavorite: Boolean?
) {

    data class Description(
        val en: String?
    )

    data class Image(
        @SerializedName("large")
        val url: String?
    )

    data class MarketData(
        @SerializedName("current_price")
        val currentPrice: CurrentPrice?,
        @SerializedName("price_change_percentage_24h")
        val priceChangePercentage24h: Double?
    )

    data class CurrentPrice(
        val usd: Float?
    )

    companion object {
        fun DocumentSnapshot.toCoinDetail(): CoinDetail? {
            val id = getString("id")
            val name = getString("name")
            val symbol = getString("symbol")
            val isFavorite = getBoolean("isFavorite")
            return if (id.isNullOrEmpty() || name.isNullOrEmpty() || symbol.isNullOrEmpty()) null
            else CoinDetail(id, name, symbol, null, null, null, null, isFavorite)
            //return this.toObject(CoinDetail::class.java)
        }
    }
}



