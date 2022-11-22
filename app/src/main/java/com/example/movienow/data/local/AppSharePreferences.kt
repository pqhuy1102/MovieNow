package com.example.movienow.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharePreferences @Inject constructor(@ApplicationContext context:Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object{
        private const val DARK_MODE_STATUS = "DarkModeStatus"
    }

    fun getDarkModeStatus() : Int{
        return preferences.getInt(DARK_MODE_STATUS, 0)
    }

    fun setDarkModeStatus(value: Int){
        preferences.edit().putInt(DARK_MODE_STATUS, value).apply()
    }

}