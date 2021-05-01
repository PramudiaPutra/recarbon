package com.dextor.recarbon.data

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
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