package com.yunusbedir.cryptocurrencypricetrackerapp.data.local.dao

import androidx.room.*
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(coins: List<Coin>)

    @Query("SELECT * FROM Coin WHERE name LIKE :search OR symbol LIKE :search")
    suspend fun selectFilteredCoins(search: String?): List<Coin>

    @Query("DELETE FROM Coin")
    suspend fun deleteAllCoins()

}