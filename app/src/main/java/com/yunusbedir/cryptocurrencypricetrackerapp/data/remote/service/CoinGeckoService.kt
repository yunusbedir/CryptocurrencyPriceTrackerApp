package com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.service

import com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.model.Coin
import okhttp3.ResponseBody
import retrofit2.http.GET

interface CoinGeckoService {

    @GET("coins/list")
    suspend fun getCoinList(): List<Coin>

}