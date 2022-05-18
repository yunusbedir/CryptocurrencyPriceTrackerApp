package com.yunusbedir.cryptocurrencypricetrackerapp.data

sealed class Resource<T> {
    data class LoadingResource<T>(val isLoading: Boolean) : Resource<T>()
    data class SuccessResource<T>(val data: T) : Resource<T>()
    data class FailedResource<T>(val error: Exception) : Resource<T>()
}
