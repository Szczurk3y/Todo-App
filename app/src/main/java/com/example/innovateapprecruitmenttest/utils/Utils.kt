package com.example.innovateapprecruitmenttest.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

const val ADD_REQUEST_CODE = 1
const val EDIT_REQUEST_CODE = 2

fun handleResult(title: String, message: String) {
    Log.i(title, message)
}

fun apiRequestError(`while`: String) {
    throw Throwable("Conneciton error while $`while`")
}

fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date? {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.add(item)
    this.value = oldValue
}

fun <T> MutableLiveData<MutableList<T>>.deleteItemAt(position: Int) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.removeAt(position)
    this.value = oldValue
}

fun <T> MutableLiveData<MutableList<T>>.updateItemAt(position: Int, item: T) {
    val oldValue = this.value ?: mutableListOf()
    oldValue.set(position, item)
    this.value = oldValue
}