package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.CoinDetail.Companion.toCoinDetail
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val coinRepository: CoinRepository
) : ViewModel() {

    private val _favoriteListLiveData = MutableLiveData<Event<List<CoinDetail>>>()
    val favoriteListLiveData: LiveData<Event<List<CoinDetail>>> = _favoriteListLiveData

    fun getMyFavoriteCoins() {
        coinRepository.getFavoriteCoinList().addOnCompleteListener {
            if (it.isSuccessful) {
                val favoriteList = it.result.documents.mapNotNull { document ->
                    document.toCoinDetail()
                }
                _favoriteListLiveData.postValue(Event(favoriteList))
            } else {
            }
        }
    }

}