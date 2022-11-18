package com.example.movienow.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favoritemovie")
    fun getAllFavoritesMovies():Observable<List<FavoriteMovie>>

    @Insert
    fun insertFavoriteMovie(movie: FavoriteMovie): Completable

    @Query("SELECT EXISTS(SELECT * FROM favoritemovie WHERE id = :movieId)")
    fun isMovieExist(movieId: Int): Single<Boolean>
}