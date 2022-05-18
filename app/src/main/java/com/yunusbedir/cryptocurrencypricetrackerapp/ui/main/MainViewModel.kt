package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseActivityViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val coinRepository: CoinRepository
) : BaseActivityViewModel() {

    private val _toolbarVisibilityLiveData = MutableLiveData<Event<Boolean>>()
    val toolbarVisibilityLiveData: LiveData<Event<Boolean>> = _toolbarVisibilityLiveData

    private val _signOutLiveData = MutableLiveData<Event<Boolean>>()
    val signOutLiveData: LiveData<Event<Boolean>> = _signOutLiveData

    fun setToolbarVisibility(visibility: Boolean) {
        _toolbarVisibilityLiveData.postValue(Event(visibility))
    }

    fun signOutUser() {
        viewModelScope.launch {
            try {
                coinRepository.clearCoinTable()
                firebaseRepository.signOut()
                _signOutLiveData.postValue(Event(true))
            } catch (e: Exception) {
            }
        }
    }

}