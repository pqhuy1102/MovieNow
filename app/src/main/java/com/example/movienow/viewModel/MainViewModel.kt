package com.example.movienow.viewModel

import androidx.lifecycle.ViewModel
import com.example.movienow.data.local.AppSharePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (private val sharePreferences: AppSharePreferences) : ViewModel() {

    fun getDarkModeStatus() : Int{
        return sharePreferences.getDarkModeStatus()
    }

    fun setDarkModeStatus(value: Int){
        sharePreferences.setDarkModeStatus(value)
    }

}