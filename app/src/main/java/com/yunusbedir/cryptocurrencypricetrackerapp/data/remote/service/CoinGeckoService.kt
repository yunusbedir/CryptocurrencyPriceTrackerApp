package com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.service

import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinGeckoService {

    @GET("coins/list")
    suspend fun getCoinList(): List<Coin>

    @GET("coins/{id}")
    suspend fun getCoinDetail(@Path("id") id: String): CoinDetail

}