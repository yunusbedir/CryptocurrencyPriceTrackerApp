package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coinhome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.data.remote.repository.CoinGeckoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinGeckoRepository: CoinGeckoRepository
) : ViewModel() {

    private val _coinListLiveData = MutableLiveData<List<Coin>>()
    val coinListLiveData: LiveData<List<Coin>> = _coinListLiveData

    fun getCoinList() {
        viewModelScope.launch {
            try {
                val response = coinGeckoRepository.getCoins()
                _coinListLiveData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}