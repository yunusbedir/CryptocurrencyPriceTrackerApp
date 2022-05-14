package com.yunusbedir.cryptocurrencypricetrackerapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _screenStateLiveData = MutableLiveData<ScreenState>()
    val screenStateLiveData: LiveData<ScreenState> = _screenStateLiveData

    private val _loginLiveData = MutableLiveData<Boolean>()
    val loginLiveData: LiveData<Boolean> = _loginLiveData

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

}