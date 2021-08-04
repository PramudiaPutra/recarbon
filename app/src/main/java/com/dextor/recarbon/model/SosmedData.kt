package com.dextor.recarbon.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SosmedData(
    val uid: String? = null,
    val imgUser: Int? = null,
    val username: String? = null,
    val location: String? = null,
    val date: String? = null,
    val imgStory: String? = null,
    val title: String? = null,
    val content: String? = null
) : Parcelable