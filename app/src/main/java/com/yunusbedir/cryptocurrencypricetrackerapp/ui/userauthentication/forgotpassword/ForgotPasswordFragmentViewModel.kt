package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordFragmentViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _forgotPasswordLivedata = MutableLiveData<Event<Boolean>>()
    val forgotPasswordLivedata: LiveData<Event<Boolean>> = _forgotPasswordLivedata

    fun forgotPassword(email: String) {
        firebaseRepository.forgotPassword(email).addOnCompleteListener {
            if (it.isSuccessful) {
                _forgotPasswordLivedata.postValue(Event(true))
            } else {
                _forgotPasswordLivedata.postValue(Event(false))
            }
        }
    }

}