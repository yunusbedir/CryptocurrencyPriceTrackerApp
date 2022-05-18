package com.yunusbedir.cryptocurrencypricetrackerapp.ui.main.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _userLiveData = MutableLiveData<Event<FirebaseUser?>>()
    val userLiveData: LiveData<Event<FirebaseUser?>> = _userLiveData


    fun getUser() {
        _userLiveData.postValue(Event(firebaseRepository.getCurrentUser()))
    }

}