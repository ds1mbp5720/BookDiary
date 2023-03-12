package com.lee.bookdiary.setting

import android.app.Application
import androidx.lifecycle.LiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.base.SingleLiveEvent

class SettingViewModel(application: Application) : BaseViewModel(application) {

    private val _backClick = SingleLiveEvent<Unit>()
    val backClick: LiveData<Unit> get() = _backClick
    fun onBackClick() {
        _backClick.call()
    }
}