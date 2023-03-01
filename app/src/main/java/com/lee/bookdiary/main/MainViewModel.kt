package com.lee.bookdiary.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lee.bookdiary.base.BaseViewModel

class MainViewModel(application: Application): BaseViewModel(application) {
    private val _currentFragmentTag = MutableLiveData<String>()
    val currentFragmentTag: LiveData<String> = _currentFragmentTag

    fun setCurrentFragment(tag: String) {
        _currentFragmentTag.value = tag
    }
}