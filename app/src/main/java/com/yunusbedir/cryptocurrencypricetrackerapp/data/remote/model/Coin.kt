package com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id") val id: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("name") val name: String?
) {
}

/*
{
        "id": "bitcoin",
        "symbol": "btc",
        "name": "Bitcoin"
 */
