package com.lee.bookdiary.data.repository

import androidx.paging.PagingData
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.data.emtity.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBookPagingData(searchString: String): Flow<PagingData<BookInfo>>

    fun getBookList(searchString: String): List<BookEntity>
}