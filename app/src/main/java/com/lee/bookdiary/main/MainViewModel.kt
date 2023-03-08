package com.lee.bookdiary.main

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lee.bookdiary.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application): BaseViewModel(application) {
    private val _currentFragmentTag = MutableLiveData<String>()
    val currentFragmentTag: LiveData<String> = _currentFragmentTag
    private val _navigationMenuClick = com.lee.bookdiary.base.SingleLiveEvent<Unit>()
    val navigationMenuClick: LiveData<Unit> get() = _navigationMenuClick
    private val _navigationCloseClick = com.lee.bookdiary.base.SingleLiveEvent<Unit>()
    val navigationCloseClick: LiveData<Unit> get() = _navigationCloseClick

    fun setCurrentFragment(tag: String) {
        _currentFragmentTag.value = tag
    }

    fun onNavigationMenuClick() {
        _navigationMenuClick.call()
    }
    fun onNavigationCloseClick() {
        _navigationCloseClick.call()
    }

}