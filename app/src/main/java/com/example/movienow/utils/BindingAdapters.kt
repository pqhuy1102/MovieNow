package com.example.movienow.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@BindingAdapter("loadImage")
fun loadImage(view:ImageView, url:String?){
    val posterUrl:String = Constants.POSTER_URL + url
    posterUrl.let{
        Glide.with(view).load(it).into(view)
    }
}

@BindingAdapter("loadVote")
fun loadVote(view: TextView, vote:Double){
    view.text = "Votes: ${vote.toString()}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("loadReleaseDate")
fun loadReleaseDate(view: TextView, releaseDate:String){
    val originalFormat = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    val date:Date = originalFormat.parse(releaseDate)
    val formattedDate = formatter.format(date)
    view.text = "Release Date: $formattedDate"
}