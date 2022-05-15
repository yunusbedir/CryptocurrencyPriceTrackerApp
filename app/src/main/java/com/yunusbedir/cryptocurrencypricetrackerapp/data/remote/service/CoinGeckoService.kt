package com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.service

import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import retrofit2.http.GET

interface CoinGeckoService {

    @GET("coins/list")
    suspend fun getCoinList(): List<Coin>

}