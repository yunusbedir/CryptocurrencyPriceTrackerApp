package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunusbedir.cryptocurrencypricetrackerapp.data.CoinRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.BaseActivityViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val coinRepository: CoinRepository
) : BaseActivityViewModel() {

    private val _loginLiveData = MutableLiveData<Event<Resource<Boolean>>>()
    val loginLiveData: LiveData<Event<Resource<Boolean>>> = _loginLiveData

    fun checkSignedIn() {
        if (firebaseRepository.getCurrentUser() != null) {
            _loginLiveData.postValue(Event(Resource.SuccessResource(true)))
        }
    }

    fun loginUser(email: String, password: String) {
        _loginLiveData.postValue(Event(Resource.LoadingResource(true)))
        firebaseRepository.login(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                viewModelScope.launch {
                    coinRepository.syncCoins()
                    _loginLiveData.postValue(Event(Resource.SuccessResource(true)))
                }
            } else {
                _loginLiveData.postValue(Event(Resource.FailedResource(it.exception ?: Exception("Failed Login!"))))
            }
        }
    }


}
