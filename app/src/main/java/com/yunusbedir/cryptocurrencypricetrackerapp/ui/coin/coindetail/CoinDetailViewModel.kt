package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _currentPriceLiveData = MutableLiveData<Event<Double>>()
    val currentPriceLiveData: LiveData<Event<Double>> = _currentPriceLiveData

    private val _coinDetailLiveData = MutableLiveData<Event<Resource<CoinDetail>>>()
    val coinDetailLiveData: LiveData<Event<Resource<CoinDetail>>> = _coinDetailLiveData

    private val _isFavoriteLiveData = MutableLiveData<Boolean>(false)
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    private lateinit var coinDetail: CoinDetail

    fun getCoinDetail(id: String) {
        viewModelScope.launch {
            _coinDetailLiveData.postValue(Event(Resource.LoadingResource(true)))
            try {
                val response = coinRepository.getCoinDetail(id)
                val isFavorite = coinRepository.isFavoriteCoin(id)
                _isFavoriteLiveData.postValue(isFavorite)
                coinDetail = response
                _coinDetailLiveData.postValue(Event(Resource.SuccessResource(response)))
            } catch (e: Exception) {
                _coinDetailLiveData.postValue(Event(Resource.FailedResource(e)))
            }
        }
    }

    private fun deleteCoinFavorite() {
        viewModelScope.launch {
            coinRepository.deleteFavoriteCoin(coinDetail.id).await()
            _isFavoriteLiveData.postValue(false)
        }
    }

    private fun addCoinFavorite() {
        viewModelScope.launch {
            coinRepository.setFavoriteCoin(coinDetail).await()
            _isFavoriteLiveData.postValue(true)

        }
    }

    fun toggleFavorite() {
        if (isFavoriteLiveData.value == true) {
            deleteCoinFavorite()
        } else {
            addCoinFavorite()
        }
    }

    fun updateCoinCurrentPrice() {
        viewModelScope.launch {
            try {
                coinRepository.getCoinDetail(coinDetail.id).marketData?.currentPrice?.usd?.let { price ->
                    _currentPriceLiveData.postValue(Event(price))
                }

            } catch (e: Exception) {
            }
        }
    }
}