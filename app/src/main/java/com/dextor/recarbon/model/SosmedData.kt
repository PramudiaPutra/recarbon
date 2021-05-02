package com.dextor.recarbon.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SosmedData(
    val imgUser: Int,
    val username: String?,
    val location: String,
    val date: String,
    val imgStory: Bitmap,
    val title: String,
    val content: String
) : Parcelable