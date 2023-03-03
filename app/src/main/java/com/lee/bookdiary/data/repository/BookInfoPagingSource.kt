package com.lee.bookdiary.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lee.bookdiary.data.BookInfo
import com.lee.bookdiary.data.emtity.mapper.toBookInfoList
import com.lee.bookdiary.data.network.KakaoAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class BookInfoPagingSource (
    private val searchString: String,
    private val ioDispatcher: CoroutineDispatcher,
    private val kakaoApiService: KakaoAPIService,
) : PagingSource<Int, BookInfo>() {

    override fun getRefreshKey(state: PagingState<Int, BookInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookInfo> {
        return try {
            val page = params.key ?: 1

            val response = withContext(ioDispatcher) {
                println("thread: ${Thread.currentThread().name}")
                kakaoApiService.getBookList(query = searchString, page = page, size = 50)
            }

            val bookList = response.body()?.documents?.toBookInfoList() ?: listOf()

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (bookList.isEmpty() || response.body()?.meta?.isEnd == true) null else page + 1

            LoadResult.Page(
                data = bookList,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}