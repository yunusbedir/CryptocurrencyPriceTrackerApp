package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserAuthenticationViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _screenStateLiveData = MutableLiveData<ScreenState>()
    val screenStateLiveData: LiveData<ScreenState> = _screenStateLiveData

    private val _loginLiveData = MutableLiveData<Boolean>()
    val loginLiveData: LiveData<Boolean> = _loginLiveData

    private val _registerLiveData = MutableLiveData<Boolean>()
    val registerLiveData: LiveData<Boolean> = _registerLiveData

    private val _forgotPasswordLivedata = MutableLiveData<Boolean>()
    val forgotPasswordLivedata: LiveData<Boolean> = _forgotPasswordLivedata

    fun loginUser(email: String, password: String) {
        _screenStateLiveData.postValue(ScreenState.ProgressState(true))
        firebaseRepository.login(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _loginLiveData.postValue(true)
                _screenStateLiveData.postValue(ScreenState.ProgressState(false))
            } else {
                _screenStateLiveData.postValue(ScreenState.ToastMessageState(it.exception?.message
                    ?: "Login failed!"))
            }
        }
    }

    fun registerUser(email: String, password: String) {
        _screenStateLiveData.postValue(ScreenState.ProgressState(true))
        firebaseRepository.register(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _registerLiveData.postValue(true)
                _screenStateLiveData.postValue(ScreenState.ProgressState(false))
            } else {
                _screenStateLiveData.postValue(ScreenState.ToastMessageState(it.exception?.message
                    ?: "Register failed!"))
            }
        }
    }

    fun forgotPassword(email: String) {
        _screenStateLiveData.postValue(ScreenState.ProgressState(true))
        firebaseRepository.forgotPassword(email).addOnCompleteListener {
            if (it.isSuccessful) {
                _forgotPasswordLivedata.postValue(true)
                _screenStateLiveData.postValue(ScreenState.ProgressState(false))
            } else {
                _screenStateLiveData.postValue(ScreenState.ToastMessageState(it.exception?.message
                    ?: "Register failed!"))
            }
        }
    }
}
