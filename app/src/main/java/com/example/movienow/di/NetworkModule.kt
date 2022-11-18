package com.example.movienow.di

import android.content.Context
import com.example.movienow.data.local.database.AppDatabase
import com.example.movienow.data.local.database.FavoriteMovieDao
import com.example.movienow.data.remote.service.MovieApiService
import com.example.movienow.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient{
        val requestInterceptor = Interceptor{ chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .addQueryParameter("session_id", Constants.SESSION_ID)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        return OkHttpClient.Builder().addInterceptor(requestInterceptor).addInterceptor(
            provideLoggingInterceptor()
        )
            .build()
    }


    @Singleton
    @Provides
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideMovieService():MovieApiService{
        return Retrofit.Builder()
            .client(provideOkHttp())
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    @Provides
    fun provideFavoriteMovieDao(appDatabase: AppDatabase):FavoriteMovieDao{
        return appDatabase.favoriteMovieDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return AppDatabase.getDatabase(appContext)
    }
}