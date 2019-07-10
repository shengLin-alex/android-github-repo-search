package com.example.mvvmbeginner.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String) {
    val context = imageView.context
    Glide.with(context)
        .load(url)
        .into(imageView)
}