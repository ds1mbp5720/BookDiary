package com.lee.bookdiary.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookInfo(
    val title: String,
    val contents: String,
    val url: String,
    val isbn: String,
    val datetime: String,
    val authors: List<String>,
    val publisher: String,
    val translators: List<String>,
    val price: Int,
    val salePrice: Int,
    val thumbnail: String,
    val status: String,
    var favorite: Boolean = false
): Parcelable
