package com.lee.bookdiary.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.base.SingleLiveEvent
import com.lee.bookdiary.data.BookInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    private val _bookInfoLiveData = MutableLiveData<BookInfo>()
    val bookInfoLiveData: LiveData<BookInfo> = _bookInfoLiveData

    fun setBookInfo(bookInfo: BookInfo) {
        _bookInfoLiveData.value = bookInfo
    }

    fun setBookFavorite(favorite: Boolean) {
        _bookInfoLiveData.value?.favorite = favorite
    }

    private val _backClick = SingleLiveEvent<Unit>()
    val backClick: LiveData<Unit> get() = _backClick
    fun onBackClick() {
        _backClick.call()
    }

    private val _favoriteClick = SingleLiveEvent<Unit>()
    val favoriteClick: LiveData<Unit> get() = _favoriteClick
    fun onFavoriteClick() {
        _favoriteClick.call()
    }
}