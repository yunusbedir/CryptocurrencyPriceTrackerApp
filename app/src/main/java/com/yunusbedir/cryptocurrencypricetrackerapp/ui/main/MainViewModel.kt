package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val coinRepository: CoinRepository
) : BaseViewModel() {

    private val _toolbarVisibilityLiveData = MutableLiveData<Event<Boolean>>()
    val toolbarVisibilityLiveData: LiveData<Event<Boolean>> = _toolbarVisibilityLiveData


    fun setToolbarVisibility(visibility: Boolean) {
        _toolbarVisibilityLiveData.postValue(Event(visibility))
    }
}