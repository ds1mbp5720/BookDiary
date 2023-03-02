package com.lee.bookdiary.search

import android.app.Application
import androidx.lifecycle.LiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.base.SingleLiveEvent

class SearchViewModel(application: Application): BaseViewModel(application) {
    private val _deleteClick = SingleLiveEvent<Unit>()
    val deleteClick: LiveData<Unit> get() =  _deleteClick

    fun onDeleteCLick() {
        _deleteClick.call()
    }

}