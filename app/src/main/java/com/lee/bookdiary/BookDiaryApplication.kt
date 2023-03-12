package com.lee.bookdiary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookDiaryApplication: Application() {
    init {
        instance = this
    }
    companion object {
        lateinit var instance: BookDiaryApplication
    }
}