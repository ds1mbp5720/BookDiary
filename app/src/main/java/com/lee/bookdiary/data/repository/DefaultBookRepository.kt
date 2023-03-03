package com.lee.bookdiary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.data.emtity.BookEntity
import com.lee.bookdiary.data.network.KakaoAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultBookRepository @Inject constructor(
    private val kakaoApiService: KakaoAPIService,
    private val ioDispatcher: CoroutineDispatcher
) : BookRepository {

    override fun getBookPagingData(searchString: String): Flow<PagingData<BookInfo>> {
        return Pager(PagingConfig(pageSize = 10)) {
            BookInfoPagingSource(searchString, ioDispatcher, kakaoApiService)
        }.flow
    }

    override fun getBookList(searchString: String): List<BookEntity> {
        return listOf()
    }

}