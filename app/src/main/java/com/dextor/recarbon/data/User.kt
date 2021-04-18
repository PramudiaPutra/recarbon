package com.dextor.recarbon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String,
    var email: String,
    var password: String
):Parcelable
