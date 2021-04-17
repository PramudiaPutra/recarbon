package org.d3if0008.recarbon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData (
    var title: String,
    var content: String,
    var age: String,
    var author: String,
    var authorImage: Int,
):Parcelable