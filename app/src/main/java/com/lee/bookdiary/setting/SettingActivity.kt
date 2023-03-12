package com.lee.bookdiary.setting

import android.os.Bundle
import androidx.activity.viewModels
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseActivity
import com.lee.bookdiary.databinding.SettingActivityBinding
import com.lee.bookdiary.eventbus.ScreenModeEvent
import com.lee.bookdiary.setting.data.SettingDataStore
import org.greenrobot.eventbus.EventBus

class SettingActivity: BaseActivity<SettingActivityBinding,SettingViewModel>() {
    override val layoutId = R.layout.setting_activity
    override val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSettingOption()
        initChoiceCallBack()
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

    private fun initSettingOption(){
        dataBinding.radioScreenModeGroup.check(
            when(SettingDataStore.getScreenMode()){
                2 -> R.id.radio_type_2
                3 -> R.id.radio_type_3
                else -> R.id.radio_type_1
            }
        )
    }

    private fun initChoiceCallBack(){
        dataBinding.radioScreenModeGroup.setOnCheckedChangeListener{ _, checkId ->
            when(checkId){
                R.id.radio_type_2 -> SettingDataStore.setScreenMode(2)
                R.id.radio_type_3 -> SettingDataStore.setScreenMode(2)
                else -> SettingDataStore.setScreenMode(1)
            }
            EventBus.getDefault().post(ScreenModeEvent())
        }
    }
}