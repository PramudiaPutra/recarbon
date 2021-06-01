package com.dextor.recarbon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData (
    var image: Int,
    var title: String,
    var content: String,
    var age: String,
    var author: String,
    var authorImage: Int,
):Parcelable