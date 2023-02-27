package com.lee.bookdiary.data.emtity

import com.google.gson.annotations.SerializedName

data class BookEntity(
    @SerializedName("title") val title: String,
    @SerializedName("contents") val contents: String,
    @SerializedName("url") val url: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("authors") val authors: List<String>,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("translators") val translators: List<String>,
    @SerializedName("price") val price: Int,
    @SerializedName("sale_price") val salePrice: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("status") val status: String,
)
