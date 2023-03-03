package com.lee.bookdiary.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.lee.bookdiary.BuildConfig
import com.lee.bookdiary.data.network.KakaoAPIService
import com.lee.bookdiary.data.network.Url
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideKakaoApiService(retrofit: Retrofit): KakaoAPIService {
        return retrofit.create(KakaoAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Url.KAKAO_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().create()
        )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @AuthInterceptor authInterceptor: Interceptor,
        @NetworkCacheInterceptor networkCacheInterceptor: Interceptor,
        @ApplicationContext appContext: Context
    ): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(appContext.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val logging = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(networkCacheInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @AuthInterceptor
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor {
            it.proceed(it.request().newBuilder().apply {
                header("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
            }.build())
        }
    }

    @Singleton
    @Provides
    @NetworkCacheInterceptor
    fun provideNetworkCacheInterceptor(): Interceptor {
        return Interceptor {
            val response = it.proceed(it.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NetworkCacheInterceptor

}