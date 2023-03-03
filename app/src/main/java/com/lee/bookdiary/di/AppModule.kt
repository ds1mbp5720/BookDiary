package com.lee.bookdiary.di

import android.content.Context
import android.content.res.loader.ResourcesProvider
import com.lee.bookdiary.data.network.KakaoAPIService
import com.lee.bookdiary.data.repository.BookRepository
import com.lee.bookdiary.data.repository.DefaultBookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


    @Singleton
    @Provides
    fun provideBookRepository(
        kakaoApiService: KakaoAPIService,
        ioDispatcher: CoroutineDispatcher,
    ): BookRepository = DefaultBookRepository(kakaoApiService, ioDispatcher)
}