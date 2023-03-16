package com.lee.bookdiary.util

import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.pickup.data.PickupBookEntity

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun <T> Context.simpleStartActivity(cls: Class<T>) {
    startActivity(Intent(this, cls))
}


fun BookInfo.toPickupBookEntity() = PickupBookEntity(
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors,
    publisher = publisher ,
    translators = translators ,
    price = price ,
    salePrice = salePrice ,
    thumbnail = thumbnail ,
    status = status
)