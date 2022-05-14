package com.yunusbedir.cryptocurrencypricetrackerapp.ui

sealed class ScreenState {
    data class ProgressState(var visibility: Boolean) : ScreenState()
    data class ToastMessageState(var message: String) : ScreenState()
}
