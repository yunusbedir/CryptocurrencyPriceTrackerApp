package com.yunusbedir.cryptocurrencypricetrackerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Coin")
data class Coin(
    @SerializedName("id") @PrimaryKey val id: String,
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
