package com.yunusbedir.cryptocurrencypricetrackerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yunusbedir.cryptocurrencypricetrackerapp.data.local.dao.CoinDao
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin


@Database(entities = [Coin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

}