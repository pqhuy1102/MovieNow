package com.example.movienow.di

import android.content.Context
import android.content.SharedPreferences
import com.example.movienow.data.local.database.AppDatabase
import com.example.movienow.data.local.database.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao {
        return appDatabase.favoriteMovieDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

}