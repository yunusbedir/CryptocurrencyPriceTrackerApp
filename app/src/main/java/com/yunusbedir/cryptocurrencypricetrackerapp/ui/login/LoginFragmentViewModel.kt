package com.yunusbedir.cryptocurrencypricetrackerapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(): ViewModel() {

    private val _dumpLiveData = MutableLiveData<String>()
    val dumpLiveData: LiveData<String> = _dumpLiveData

    fun dumpText(){
        viewModelScope.launch {
            delay(100)
            _dumpLiveData.postValue("Hello! I am Login Screen")
        }
    }

}