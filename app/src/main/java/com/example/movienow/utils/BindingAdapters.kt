package com.example.movienow.utils

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.time.format.DateTimeFormatter

@BindingAdapter("loadImage")
fun loadImage(view:ImageView, url:String?){
    val posterUrl:String = Constants.POSTER_URL + url
    posterUrl.let{
        Glide.with(view).load(it).into(view)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("loadReleaseDate")
fun loadReleaseDate(view: TextView, date:String){
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val formattedDate = date.format(dateFormatter)
    view.text = formattedDate
}