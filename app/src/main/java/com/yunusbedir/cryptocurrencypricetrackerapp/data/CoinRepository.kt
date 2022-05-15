package com.yunusbedir.cryptocurrencypricetrackerapp.data

import com.yunusbedir.cryptocurrencypricetrackerapp.data.local.dao.CoinDao
import com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.service.CoinGeckoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinGeckoService: CoinGeckoService,
    private val coinDao: CoinDao
) {
    suspend fun syncCoins() = withContext(Dispatchers.IO) {
        val response = coinGeckoService.getCoinList()
        if (response.isNullOrEmpty().not()) {
            coinDao.deleteAllCoins()
            coinDao.insertAllCoins(response)
        }
    }

    suspend fun getCoins(search: String = "") = withContext(Dispatchers.IO) {
        val newSearch = "%$search%"
        coinDao.selectFilteredCoins(newSearch)
    }

    suspend fun getCoinDetail(id: String) = withContext(Dispatchers.IO) {
        coinGeckoService.getCoinDetail(id)
    }
}