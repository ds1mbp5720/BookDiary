package com.lee.bookdiary.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseActivity
import com.lee.bookdiary.databinding.ActivityMainBinding
import com.lee.bookdiary.dialog.DialogMessage
import com.lee.bookdiary.eventbus.ScreenModeEvent
import com.lee.bookdiary.search.SearchFragment
import com.lee.bookdiary.setting.SettingActivity
import com.lee.bookdiary.setting.ThemeUtil
import com.lee.bookdiary.setting.data.SettingDataStore
import com.lee.bookdiary.util.simpleStartActivity
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(){
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
        super.initViews()
        when(viewModel.currentFragmentTag.value) {
            null -> showFragment(SearchFragment.newInstance(), SearchFragment.TAG)
            SearchFragment.TAG -> showFragment(SearchFragment.newInstance(), SearchFragment.TAG)
        }

    }

    override fun initObserve() {
        super.initObserve()

        viewModel.navigationMenuClick.observe(this){
            dataBinding.drawerLayout.openDrawer(dataBinding.navigationViewMain)
        }
        viewModel.navigationCloseClick.observe(this){
            dataBinding.drawerLayout.closeDrawer(dataBinding.navigationViewMain)
        }
        viewModel.readCountClick.observe(this){
            //Todo 읽은 책 수 클릭 이벤트
        }
        viewModel.settingClick.observe(this){
            simpleStartActivity(SettingActivity::class.java)
        }
        viewModel.developerClick.observe(this){
            //Todo developer info page
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction()
                .hide(it)
                .commitAllowingStateLoss()
        }
        findFragment?.let {
            supportFragmentManager.beginTransaction()
                .show(it)
                .commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (dataBinding.drawerLayout.isOpen) {
            dataBinding.drawerLayout.close()
            return
        }
        DialogMessage(getString(R.string.str_destroy_app), getString(R.string.str_confirm), getString(R.string.str_cancel)).onRightBtn {
            finish()
        }.show(supportFragmentManager, "")
    }

    @Subscribe
    fun onEvent(e: ScreenModeEvent) {
        ThemeUtil.applyTheme(SettingDataStore.getScreenMode())
        recreate()
    }
}