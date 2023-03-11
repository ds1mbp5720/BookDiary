package com.lee.bookdiary.setting

import android.os.Bundle
import androidx.activity.viewModels
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseActivity
import com.lee.bookdiary.databinding.SettingActivityBinding

class SettingActivity: BaseActivity<SettingActivityBinding,SettingViewModel>() {
    override val layoutId = R.layout.setting_activity
    override val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.backClick.observe(this){
            finish()
        }
    }

    override fun initViews() {
        super.initViews()
    }
}