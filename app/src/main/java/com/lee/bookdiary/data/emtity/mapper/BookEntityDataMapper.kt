package com.lee.bookdiary.data.emtity.mapper

import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.data.emtity.BookEntity

fun BookEntity.toBookInfo() = BookInfo(
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors,
    publisher = publisher,
    translators = translators,
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    status = status,
    favorite = false
)

fun List<BookEntity>.toBookInfoList() = map { it.toBookInfo() }