package com.lee.bookdiary.dialog

import android.app.Application
import androidx.lifecycle.LiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.base.SingleLiveEvent

class DialogMessageViewModel(application: Application) : BaseViewModel(application) {

    private val _leftClick = SingleLiveEvent<Unit>()
    val leftClick: LiveData<Unit> get() = _leftClick
    private val _rightClick = SingleLiveEvent<Unit>()
    val rightClick: LiveData<Unit> get() = _rightClick
    private val _closeClick = SingleLiveEvent<Unit>()
    val closeClick: LiveData<Unit> get() = _closeClick

    fun onLeftClick() {
        _leftClick.call()
    }
    fun onRightClick() {
        _rightClick.call()
    }
    fun onCloseClick() {
        _closeClick.call()
    }

}