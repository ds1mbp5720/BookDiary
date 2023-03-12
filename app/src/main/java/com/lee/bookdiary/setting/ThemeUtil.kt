package com.lee.bookdiary.setting

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil {
    fun applyTheme(modeType: Int) {
        when (modeType) {
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            3 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}