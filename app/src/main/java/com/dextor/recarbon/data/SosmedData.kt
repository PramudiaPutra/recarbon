package com.dextor.recarbon.data

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SosmedData(
    val imgUser: Int,
    val username: String,
    val location: String,
    val date: String,
    val imgStory: Int,
    val title: String,
    val content: String
) : Parcelable