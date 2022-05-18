package com.yunusbedir.cryptocurrencypricetrackerapp.ui.userauthentication.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yunusbedir.cryptocurrencypricetrackerapp.data.Resource
import com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase.FirebaseRepository
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordFragmentViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _forgotPasswordLivedata = MutableLiveData<Event<Resource<Boolean>>>()
    val forgotPasswordLivedata: LiveData<Event<Resource<Boolean>>> = _forgotPasswordLivedata

    fun forgotPassword(email: String) {
        _forgotPasswordLivedata.postValue(Event(Resource.LoadingResource(true)))
        firebaseRepository.forgotPassword(email).addOnCompleteListener {
            if (it.isSuccessful) {
                _forgotPasswordLivedata.postValue(Event(Resource.SuccessResource(true)))
            } else {
                _forgotPasswordLivedata.postValue(Event(Resource.FailedResource(it.exception
                    ?: Exception("Can not sent email!"))))
            }
        }
    }

}