package com.dextor.recarbon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryData (
    var categoryImage: Int,
    var categoryTitle: String,
    var color: String
):Parcelable
