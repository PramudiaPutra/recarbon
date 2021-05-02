package com.dextor.recarbon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryData(
    var date: String,
    var icon: Int,
    val title: String,
    var time: String,
    var description: String,
    var carbon: String
):Parcelable