package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.R
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _coinDetailLiveData = MutableLiveData<CoinDetail>()
    val coinDetailLiveData: LiveData<CoinDetail> = _coinDetailLiveData

    private val _isFavoriteLiveData = MutableLiveData<Boolean>(false)
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    fun getCoinDetail(id: String) {
        viewModelScope.launch {
            try {
                val response = coinRepository.getCoinDetail(id)
                val isFavorite = coinRepository.isFavoriteCoin(id)
                _isFavoriteLiveData.postValue(isFavorite)
                _coinDetailLiveData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deleteCoinFavorite() {
        viewModelScope.launch {
            val id = coinDetailLiveData.value?.id
            id?.let {
                coinRepository.deleteFavoriteCoin(it).await()
                _isFavoriteLiveData.postValue(false)
            }
        }
    }

    private fun addCoinFavorite() {
        viewModelScope.launch {
            coinDetailLiveData.value?.let {
                coinRepository.setFavoriteCoin(it).await()
                _isFavoriteLiveData.postValue(true)
            }
        }
    }

    fun toggleFavorite() {
        if (isFavoriteLiveData.value == true) {
            deleteCoinFavorite()
        } else {
            addCoinFavorite()
        }
    }
}