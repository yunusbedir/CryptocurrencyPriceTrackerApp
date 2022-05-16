package com.yunusbedir.cryptocurrencypricetrackerapp.ui.coin.coinhome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yunusbedir.cryptocurrencypricetrackerapp.data.model.Coin
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    private val _coinListLiveData = MutableLiveData<Event<List<Coin>>>()
    val coinListLiveData: LiveData<Event<List<Coin>>> = _coinListLiveData

    private val _logoutLiveData = MutableLiveData<Event<Boolean>>()
    val logoutLiveData: LiveData<Event<Boolean>> = _logoutLiveData

    fun filterCoins(search: String) {
        _screenStateLiveData.postValue(Event(ScreenState.ProgressState(true)))
        viewModelScope.launch {
            try {
                val coinList = coinRepository.getCoins(search)
                _coinListLiveData.postValue(Event(coinList))
                _screenStateLiveData.postValue(Event(ScreenState.ProgressState(false)))
            } catch (e: Exception) {
                _screenStateLiveData.postValue(Event(ScreenState.ToastMessageState(e.message
                    ?: "Can not found coin list!")))
            }
        }
    }

    fun logout() {
        _screenStateLiveData.postValue(Event(ScreenState.ProgressState(true)))
        viewModelScope.launch {
            try {
                firebaseAuth.signOut()
                _logoutLiveData.postValue(Event(true))
                _screenStateLiveData.postValue(Event(ScreenState.ProgressState(false)))
            } catch (e: Exception) {
                _screenStateLiveData.postValue(Event(ScreenState.ToastMessageState(e.message
                    ?: "Can not found coin list!")))
            }
        }
    }
}