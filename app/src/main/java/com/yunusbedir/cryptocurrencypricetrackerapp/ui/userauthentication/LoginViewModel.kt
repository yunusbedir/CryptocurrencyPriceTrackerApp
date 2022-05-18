package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication

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
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val coinRepository: CoinRepository
) : BaseActivityViewModel() {

    private val _loginLiveData = MutableLiveData<Event<Boolean>>()
    val loginLiveData: LiveData<Event<Boolean>> = _loginLiveData

    fun checkSignedIn() {
        if (firebaseRepository.getCurrentUser() != null) {
            _loginLiveData.postValue(Event(true))
            changeScreenState(ScreenState.ProgressState(false))
        }
    }

    fun loginUser(email: String, password: String) {
        firebaseRepository.login(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                viewModelScope.launch {
                    coinRepository.syncCoins()
                    _loginLiveData.postValue(Event(true))
                }
            } else {
                _loginLiveData.postValue(Event(false))
            }
        }
    }


}
