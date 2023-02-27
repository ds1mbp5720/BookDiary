package com.lee.bookdiary.main

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.viewModels
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseActivity
import com.lee.bookdiary.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initObserve() {
        super.initObserve()
    }
}