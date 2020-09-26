package com.example.innovateapprecruitmenttest.utils

import android.util.Log

const val ADD_REQUEST_CODE = 1
const val EDIT_REQUEST_CODE = 2

fun handleResult(title: String, message: String) {
    Log.i(title, message)
}