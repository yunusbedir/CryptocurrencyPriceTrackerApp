package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAuthenticationViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _screenStateLiveData = MutableLiveData<Event<ScreenState>>()
    val screenStateLiveData: LiveData<Event<ScreenState>> = _screenStateLiveData

    private val _loginLiveData = MutableLiveData<Event<Boolean>>()
    val loginLiveData: LiveData<Event<Boolean>> = _loginLiveData

    private val _registerLiveData = MutableLiveData<Event<Boolean>>()
    val registerLiveData: LiveData<Event<Boolean>> = _registerLiveData

    private val _forgotPasswordLivedata = MutableLiveData<Event<Boolean>>()
    val forgotPasswordLivedata: LiveData<Event<Boolean>> = _forgotPasswordLivedata

    fun loginUser(email: String, password: String) {
        _screenStateLiveData.postValue(Event(ScreenState.ProgressState(true)))
        firebaseRepository.login(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _loginLiveData.postValue(Event(true))
                _screenStateLiveData.postValue(Event(ScreenState.ProgressState(false)))
            } else {
                _screenStateLiveData.postValue(Event(ScreenState.ToastMessageState(it.exception?.message
                    ?: "Login failed!")))
            }
        }
    }

    fun registerUser(email: String, password: String) {
        _screenStateLiveData.postValue(Event(ScreenState.ProgressState(true)))
        firebaseRepository.register(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _registerLiveData.postValue(Event(true))
                _screenStateLiveData.postValue(Event(ScreenState.ProgressState(false)))
            } else {
                _screenStateLiveData.postValue(Event(ScreenState.ToastMessageState(it.exception?.message
                    ?: "Register failed!")))
            }
        }
    }

    fun forgotPassword(email: String) {
        _screenStateLiveData.postValue(Event(ScreenState.ProgressState(true)))
        firebaseRepository.forgotPassword(email).addOnCompleteListener {
            if (it.isSuccessful) {
                _forgotPasswordLivedata.postValue(Event(true))
                _screenStateLiveData.postValue(Event(ScreenState.ProgressState(false)))
            } else {
                _screenStateLiveData.postValue(Event(ScreenState.ToastMessageState(it.exception?.message
                    ?: "Register failed!")))
            }
        }
    }
}
