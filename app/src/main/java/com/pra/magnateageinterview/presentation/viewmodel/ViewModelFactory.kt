package com.pra.myapplication.UI.Util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pra.magnateageinterview.presentation.viewmodel.MainActivityViewModel

class ViewModelFactory (private val app: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
