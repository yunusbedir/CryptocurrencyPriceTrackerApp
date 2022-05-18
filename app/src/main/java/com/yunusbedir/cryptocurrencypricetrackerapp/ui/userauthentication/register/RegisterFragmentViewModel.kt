package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _registerLiveData = MutableLiveData<Event<Boolean>>()
    val registerLiveData: LiveData<Event<Boolean>> = _registerLiveData

    fun registerUser(email: String, password: String) {
        firebaseRepository.register(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _registerLiveData.postValue(Event(true))
            }else{
                _registerLiveData.postValue(Event(false))
            }
        }
    }

}