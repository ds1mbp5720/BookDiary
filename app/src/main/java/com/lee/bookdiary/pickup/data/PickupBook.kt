package com.lee.bookdiary.pickup.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
class PickupBook() {
    @NonNull
    @ColumnInfo(name = "bookId") // 구분 id
    var id: Int = 0

    @ColumnInfo(name = "pickupBook")
    lateinit var pickupBook: PickupBookEntity

    @Ignore
    constructor(id: Int, pickupBook: PickupBookEntity) : this() {
        this.id = id
        this.pickupBook = pickupBook
    }

    @Ignore
    constructor(pickupBook: PickupBookEntity) : this() {
        this.pickupBook = pickupBook
    }
}