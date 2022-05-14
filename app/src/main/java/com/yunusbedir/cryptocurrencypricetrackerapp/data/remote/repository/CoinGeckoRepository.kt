package com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.repository

import com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.service.CoinGeckoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinGeckoRepository @Inject constructor(
    private val coinGeckoService: CoinGeckoService
) {
    suspend fun getCoins() = withContext(Dispatchers.IO) {
        coinGeckoService.getCoinList()
    }
}