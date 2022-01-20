package com.mhmd.countriesapp.framework.presentation.utils

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner

class ConnectivityManager
constructor(
    context: Context,
) {
    private val connectionLiveData = ConnectionLiveData(context)

    // observe this in ui
    val isNetworkAvailable = mutableStateOf(false)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}
