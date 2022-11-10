package com.example.movienow.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun loadImage(view:ImageView, url:String?){

    url?.let{
        Glide.with(view).load(it).into(view)
    }

}