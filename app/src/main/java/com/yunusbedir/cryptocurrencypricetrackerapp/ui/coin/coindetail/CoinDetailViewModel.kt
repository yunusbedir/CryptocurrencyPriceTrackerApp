package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coindetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : BaseViewModel() {

    private val _coinDetailLiveData = MutableLiveData<CoinDetail>()
    val coinDetailLiveData: LiveData<CoinDetail> = _coinDetailLiveData

    private val _isFavoriteLiveData = MutableLiveData<Boolean>(false)
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData

    fun getCoinDetail(id: String) {
        _screenStateLiveData.postValue(Event(ScreenState.ProgressState(true)))
        viewModelScope.launch {
            try {
                val response = coinRepository.getCoinDetail(id)
                val isFavorite = coinRepository.isFavoriteCoin(id)
                _isFavoriteLiveData.postValue(isFavorite)
                _coinDetailLiveData.postValue(response)
                _screenStateLiveData.postValue(Event(ScreenState.ProgressState(false)))
            } catch (e: Exception) {
                _screenStateLiveData.postValue(Event(ScreenState.ToastMessageState(e.message
                    ?: "Can not found coin detail!")))
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