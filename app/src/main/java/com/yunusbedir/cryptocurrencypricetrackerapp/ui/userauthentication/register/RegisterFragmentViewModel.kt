package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource

import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _registerLiveData = MutableLiveData<Event<Resource<Boolean>>>()
    val registerLiveData: LiveData<Event<Resource<Boolean>>> = _registerLiveData

    fun registerUser(email: String, password: String) {
        _registerLiveData.postValue(Event(Resource.LoadingResource(true)))
        firebaseRepository.register(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _registerLiveData.postValue(Event(Resource.SuccessResource(true)))
            } else {
                _registerLiveData.postValue(Event(Resource.FailedResource(it.exception ?: Exception("Can not registered user!"))))
            }
        }
    }

}