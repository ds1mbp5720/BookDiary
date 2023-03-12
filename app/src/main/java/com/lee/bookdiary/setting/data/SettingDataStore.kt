package com.lee.bookdiary.setting.data

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.lee.bookdiary.BookDiaryApplication.Companion.instance
import com.lee.bookdiary.util.settingDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

object SettingDataStore {
    private val settingDataStore by lazy { instance.settingDataStore }


    fun getScreenMode(): Int = runBlocking {
        val screenModeKey = intPreferencesKey("ScreenModeInt")
        return@runBlocking settingDataStore.data.map { it[screenModeKey] }.first() ?: 1
    }

    fun setScreenMode(modeType: Int) = runBlocking {
        val screenModeKey = intPreferencesKey("ScreenModeInt")
        settingDataStore.edit {
            it[screenModeKey] = modeType
        }
    }
}