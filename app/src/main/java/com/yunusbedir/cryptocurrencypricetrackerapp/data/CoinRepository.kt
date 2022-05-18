package com.yunusbedir.cryptocurrencypricetrackerapp.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yunusbedir.cryptocurrencypricetrackerapp.data.local.dao.CoinDao
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.service.CoinGeckoService
import com.yunusbedir.cryptocurrencypricetrackerapp.util.FirebaseConstants.COLLECTION_FAVORITES
import com.yunusbedir.cryptocurrencypricetrackerapp.util.FirebaseConstants.COLLECTION_USERS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val coinGeckoService: CoinGeckoService,
    private val coinDao: CoinDao,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
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

    private fun getUserCollection(email: String) =
        firebaseFirestore.collection(COLLECTION_USERS).document(email)

    fun setFavoriteCoin(coin: CoinDetail) = getUserCollection(firebaseAuth.currentUser!!.uid)
        .collection(COLLECTION_FAVORITES).document(coin.id).set(coin)

    fun deleteFavoriteCoin(id: String) = getUserCollection(firebaseAuth.currentUser!!.uid)
        .collection(COLLECTION_FAVORITES).document(id).delete()

    fun getFavoriteCoinList() = getUserCollection(firebaseAuth.currentUser!!.uid)
        .collection(COLLECTION_FAVORITES).get()

    suspend fun isFavoriteCoin(coinDetailId: String): Boolean {
        return try {
            getUserCollection(firebaseAuth.currentUser!!.uid)
                .collection(COLLECTION_FAVORITES).document(coinDetailId).get()
                .await()
                .getString("id") != null
        } catch (e: Exception) {
            false
        }
    }

    suspend fun clearCoinTable() = withContext(Dispatchers.IO) {
        coinDao.deleteAllCoins()
    }
}