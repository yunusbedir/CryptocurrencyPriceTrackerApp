package com.yunusbedir.cryptocurrencypricetrackerapp.data.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.getField
import com.google.gson.annotations.SerializedName


data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    @SerializedName("hashing_algorithm")
    val hashing: String? = null,
    @SerializedName("market_cap_rank")
    val marketCapRank: String? = null,
    @SerializedName("market_data")
    val marketData: MarketData? = null,
    val description: Description? = null,
    val image: Image? = null,
    var isFavorite: Boolean? = null
) {

    data class Description(
        val en: String? = null
    )

    data class Image(
        @SerializedName("large")
        val url: String? = null
    )

    data class MarketData(
        @SerializedName("current_price")
        val currentPrice: CurrentPrice? = null,
        @SerializedName("price_change_percentage_24h")
        val priceChangePercentage24h: Double? = null
    )

    data class CurrentPrice(
        val usd: Double? = null
    )

    companion object {
        fun DocumentSnapshot.toCoinDetail(): CoinDetail? {
            val id = getString("id")
            val name = getString("name")
            val symbol = getString("symbol")
            val isFavorite = getBoolean("isFavorite")
            val marketCapRank = getString("marketCapRank")
            val hashing = getString("hashing")
            val marketData = try {
                MarketData(
                    currentPrice = CurrentPrice(
                        ((get("marketData") as Map<String,Any>)["currentPrice"] as Map<String,Double>)["usd"]
                    ),
                    priceChangePercentage24h = (get("marketData") as Map<String,Any>)["priceChangePercentage24h"].toString().toDouble()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            val image = try {
                Image(
                    (get("image") as Map<String,String>)["url"]
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            return if (id.isNullOrEmpty() || name.isNullOrEmpty() || symbol.isNullOrEmpty()) null
            else CoinDetail(id, name, symbol, hashing, marketCapRank, marketData, null, image, isFavorite)
            //return this.toObject(CoinDetail::class.java)
        }
    }
}



