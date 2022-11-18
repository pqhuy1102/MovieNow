package com.example.movienow.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteMovie")
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name="title") val title:String,
    @ColumnInfo(name="poster_path") val posterPath:String,
    @ColumnInfo(name="release_date") val releaseDate:String,
)