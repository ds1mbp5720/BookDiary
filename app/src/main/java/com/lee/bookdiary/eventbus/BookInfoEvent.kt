package com.lee.bookdiary.eventbus

import com.lee.bookdiary.data.BookInfo

class BookInfoEvent(bookInfo: BookInfo) {
    val bookInfo = bookInfo
}