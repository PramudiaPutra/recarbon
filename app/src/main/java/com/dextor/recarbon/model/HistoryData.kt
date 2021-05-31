package com.dextor.recarbon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryData(
    var date: String? = null,
    var icon: Int? = null,
    val title: String? = null,
    var time: String? = null,
    var description: String? = null,
    var carbon: String? = null
):Parcelable