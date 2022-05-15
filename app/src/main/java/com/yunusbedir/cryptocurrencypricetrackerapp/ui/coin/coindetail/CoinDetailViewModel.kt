package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _coinDetailLiveData = MutableLiveData<CoinDetail>()
    val coinDetailLiveData: LiveData<CoinDetail> = _coinDetailLiveData

    fun getCoinDetail(id: String) {
        viewModelScope.launch {
            try {
                val response =  coinRepository.getCoinDetail(id)
                _coinDetailLiveData.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}