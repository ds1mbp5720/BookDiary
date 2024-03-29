package com.lee.bookdiary.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseActivity
import com.lee.bookdiary.databinding.ActivityMainBinding
import com.lee.bookdiary.dialog.BaseDialogMessage
import com.lee.bookdiary.dialog.DialogMessage
import com.lee.bookdiary.eventbus.ScreenModeEvent
import com.lee.bookdiary.pickup.PickupFragment
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
        setFragment(SearchFragment.newInstance(), SearchFragment.TAG)
        setFragment(PickupFragment.newInstance(), PickupFragment.TAG)
    }

    override fun onResume() {
        super.onResume()
        val pickupFragment = supportFragmentManager.findFragmentByTag(PickupFragment.TAG)
        if (pickupFragment != null) {
            supportFragmentManager.beginTransaction()
                .hide(pickupFragment)
                .commitAllowingStateLoss()
        }
        initSettingNavigation()
    }

    override fun initViews() {
        super.initViews()
        when(viewModel.currentFragmentTag.value) {
            null -> showFragment(SearchFragment.TAG)
            SearchFragment.TAG -> showFragment(SearchFragment.TAG)
            PickupFragment.TAG -> showFragment(PickupFragment.TAG)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun initObserve() {
        super.initObserve()

        viewModel.navigationMenuClick.observe(this){
            dataBinding.drawerLayout.openDrawer(dataBinding.navigationViewMain)
        }
        viewModel.navigationCloseClick.observe(this){
            dataBinding.drawerLayout.closeDrawer(dataBinding.navigationViewMain)
        }
        viewModel.readCountClick.observe(this){
            dataBinding.twPickupBookCnt.setTextColor(R.color.teal_200)
            viewModel.setCurrentFragment(PickupFragment.TAG)
        }
        viewModel.settingClick.observe(this){
            simpleStartActivity(SettingActivity::class.java)
        }
        viewModel.developerClick.observe(this){
            //Todo developer info page
        }
        viewModel.currentFragmentTag.observe(this){
            when(it){
                null -> showFragment(SearchFragment.TAG)
                SearchFragment.TAG -> showFragment(SearchFragment.TAG)
                PickupFragment.TAG -> showFragment(PickupFragment.TAG)
            }
        }
    }
    private fun setFragment(fragment: Fragment, tag: String){
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, fragment, tag)
            .commitAllowingStateLoss()
    }
    private fun showFragment(tag: String) {
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
        }
    }

    private fun initSettingNavigation(){
        dataBinding.recyclerNavigation.adapter = NavigationAdapter{
            when(it){
                NavigationAdapter.Navigation.SEARCH ->{
                    viewModel.setCurrentFragment(SearchFragment.TAG)
                    dataBinding.drawerLayout.closeDrawer(dataBinding.navigationViewMain)
                }
                NavigationAdapter.Navigation.PICKUP ->{
                    viewModel.setCurrentFragment(PickupFragment.TAG)
                    dataBinding.drawerLayout.closeDrawer(dataBinding.navigationViewMain)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (dataBinding.drawerLayout.isOpen) {
            dataBinding.drawerLayout.close()
            return
        }
       /*DialogMessage(getString(R.string.str_destroy_app), getString(R.string.str_confirm), getString(R.string.str_cancel)).onRightBtn {
            finish()
        }.show(supportFragmentManager, "")*/
        DialogMessage(getString(R.string.str_destroy_app_title), getString(R.string.str_confirm), getString(R.string.str_cancel), msg = getString(R.string.str_destroy_app_text),
            rightClickAction =  { finish() }
        ).show(supportFragmentManager, "")
    }

    @Subscribe
    fun onEvent(e: ScreenModeEvent) {
        ThemeUtil.applyTheme(SettingDataStore.getScreenMode())
        recreate()
    }
}