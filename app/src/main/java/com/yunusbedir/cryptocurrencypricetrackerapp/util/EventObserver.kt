package com.yunusbedir.cryptocurrencypricetrackerapp.util

import androidx.lifecycle.Observer
import com.yunusbedir.cryptocurrencypricetrackerapp.util.Event

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}