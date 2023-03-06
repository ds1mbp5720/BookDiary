package com.lee.bookdiary.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.data.BookInfo
import javax.inject.Inject

class DetailViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    private val _bookInfoLiveData = MutableLiveData<BookInfo>()
    val bookInfoLiveData: LiveData<BookInfo> = _bookInfoLiveData

    fun setBookInfo(bookInfo: BookInfo) {
        _bookInfoLiveData.value = bookInfo
    }

}