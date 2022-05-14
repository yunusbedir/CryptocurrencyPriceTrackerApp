package com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.service

import okhttp3.ResponseBody
import retrofit2.http.GET

interface CoinGeckoService {

    @GET("coins")
    suspend fun getCoinList(): ResponseBody

}