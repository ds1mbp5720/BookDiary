package com.lee.bookdiary.pickup.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class PickupBookEntity(
    @PrimaryKey
    val id : Int,
    val title: String = "",
    val contents: String = "",
    val url: String = "",
    val isbn: String = "",
    val datetime: String = "",
    val authors: List<String>? = null,
    val publisher: String = "",
    val translators: List<String>? = null,
    val price: Int = 0,
    val salePrice: Int = 0,
    val thumbnail: String = "",
    val status: String = ""
)
