package com.lee.bookdiary.data.network

import com.lee.bookdiary.data.response.BookListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoAPIService {
    @GET("/v3/search/book")
    suspend fun getBookList(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Response<BookListResponse>

}