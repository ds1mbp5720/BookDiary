package com.lee.bookdiary.pickup.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PickupBookEntity(
    @PrimaryKey
    val id : Int,
    val title: String,
    val contents: String,
    val url: String,
    val isbn: String,
    val datetime: String,
    //val authors: List<String>,
    val publisher: String,
    //val translators: List<String>,
    val price: Int,
    val salePrice: Int,
    val thumbnail: String,
    val status: String
)
