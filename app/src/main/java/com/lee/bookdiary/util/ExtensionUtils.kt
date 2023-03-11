package com.lee.bookdiary.util

import android.content.Context
import android.content.Intent

fun <T> Context.simpleStartActivity(cls: Class<T>) {
    startActivity(Intent(this, cls))
}