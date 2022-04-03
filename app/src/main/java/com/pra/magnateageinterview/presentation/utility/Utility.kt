package com.pra.magnateageinterview.presentation.utility

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pra.magnateageinterview.R

object Utility {


    fun setImageResourceForBanner(imageView: ImageView?, url: String) {
        var url = url
        if (url.length == 0) {
            url = "temp"
        }

    }

}