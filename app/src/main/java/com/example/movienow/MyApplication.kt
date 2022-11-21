package com.example.movienow

import android.app.Application
import com.example.movienow.data.local.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

//trigger generate code from Hilt
@HiltAndroidApp
class MyApplication : Application(){
//    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
