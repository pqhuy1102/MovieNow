package com.example.movienow.data.local

import android.content.Context
import androidx.preference.PreferenceManager

class AppSharePreferences(context:Context) {
    companion object{
        private const val DARKMODE_STATUS = ""
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = sharedPreferences.getInt(DARKMODE_STATUS, 0)
    set(value) = sharedPreferences.edit().putInt(DARKMODE_STATUS, value).apply()
}