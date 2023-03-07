package com.lee.bookdiary.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun getDateString(date: String, patternBefore: String, patternAfter: String): String {
    val inputFormat = SimpleDateFormat(patternBefore)
    val outputFormat = SimpleDateFormat(patternAfter)
    val resultDate = inputFormat.parse(date)

    return outputFormat.format(resultDate ?: "")
}