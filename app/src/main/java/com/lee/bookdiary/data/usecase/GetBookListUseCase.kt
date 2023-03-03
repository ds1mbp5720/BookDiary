package com.lee.bookdiary.data.usecase

import androidx.paging.PagingData
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.data.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(searchString: String): Flow<PagingData<BookInfo>> {
        return bookRepository.getBookPagingData(searchString)
    }
}