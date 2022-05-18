package com.yunusbedir.cryptocurrencypricetrackerapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event

abstract class BaseActivityViewModel : ViewModel() {

    private val _screenStateLiveData = MutableLiveData<Event<ScreenState>>()
    val screenStateLiveData: LiveData<Event<ScreenState>> = _screenStateLiveData

    fun changeScreenState(screenState: ScreenState) {
        _screenStateLiveData.postValue(Event(screenState))
    }
}