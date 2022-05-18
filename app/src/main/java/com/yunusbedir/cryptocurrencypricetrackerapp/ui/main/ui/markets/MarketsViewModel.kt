package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.markets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketsViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _coinListLiveData = MutableLiveData<Event<Resource<List<Coin>>>>()
    val coinListLiveData: LiveData<Event<Resource<List<Coin>>>> = _coinListLiveData

    fun filterCoins(search: String) {
        _coinListLiveData.postValue(Event(Resource.LoadingResource(true)))
        viewModelScope.launch {
            try {
                val coinList = coinRepository.getCoins(search)
                _coinListLiveData.postValue(Event(Resource.SuccessResource(coinList)))
            } catch (e: Exception) {
                _coinListLiveData.postValue(Event(Resource.FailedResource(e)))
            }
        }
    }

}