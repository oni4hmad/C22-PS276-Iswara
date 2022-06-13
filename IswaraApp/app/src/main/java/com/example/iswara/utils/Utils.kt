package com.example.iswara.utils

import java.text.SimpleDateFormat
import java.util.*

fun dateToString(date: Date): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    formatter.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    return formatter.format(date) ?: String()
}

fun formatDateString(dateStr: String): String {
    var formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    formatter.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    val date = formatter.parse(dateStr)
    formatter = SimpleDateFormat("d MMMM yyyy")
    return date?.let { formatter.format(it) } ?: String()
}