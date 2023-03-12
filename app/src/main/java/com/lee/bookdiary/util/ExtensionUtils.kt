package com.lee.bookdiary.util

import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun <T> Context.simpleStartActivity(cls: Class<T>) {
    startActivity(Intent(this, cls))
}