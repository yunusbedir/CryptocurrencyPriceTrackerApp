package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coinhome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _coinListLiveData = MutableLiveData<List<Coin>>()
    val coinListLiveData: LiveData<List<Coin>> = _coinListLiveData

    fun syncCoins() {
        viewModelScope.launch {
            try {
                coinRepository.syncCoins()
                val coinList = coinRepository.getCoins()
                _coinListLiveData.postValue(coinList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun filterCoins(search: String) {
        viewModelScope.launch {
            try {
                val coinList = coinRepository.getCoins(search)
                _coinListLiveData.postValue(coinList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}