package com.lee.bookdiary.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lee.bookdiary.base.BaseViewModel
import com.lee.bookdiary.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application): BaseViewModel(application) {

    private val _currentFragmentTag = MutableLiveData<String>()
    val currentFragmentTag: LiveData<String> = _currentFragmentTag
    private val _navigationMenuClick = SingleLiveEvent<Unit>()
    val navigationMenuClick: LiveData<Unit> get() = _navigationMenuClick
    private val _navigationCloseClick = SingleLiveEvent<Unit>()
    val navigationCloseClick: LiveData<Unit> get() = _navigationCloseClick
    private val _readCountClick = SingleLiveEvent<Unit>()
    val readCountClick: LiveData<Unit> get() = _readCountClick
    private val _settingClick = SingleLiveEvent<Unit>()
    val settingClick: LiveData<Unit> get() = _settingClick
    private val _developerClick = SingleLiveEvent<Unit>()
    val developerClick: LiveData<Unit> get() = _developerClick

    fun setCurrentFragment(tag: String) {
        _currentFragmentTag.value = tag
    }
    fun onNavigationMenuClick() {
        _navigationMenuClick.call()
    }
    fun onReadCountClick() {
        _readCountClick.call()
    }
    fun onNavigationCloseClick() {
        _navigationCloseClick.call()
    }
    fun onSettingClick() {
        _settingClick.call()
    }
    fun onDeveloperClick() {
        _developerClick.call()
    }

}