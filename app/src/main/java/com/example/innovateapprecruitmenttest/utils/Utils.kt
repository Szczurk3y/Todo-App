package com.example.innovateapprecruitmenttest.utils

import android.util.Log
import java.lang.Exception

const val ADD_REQUEST_CODE = 1
const val EDIT_REQUEST_CODE = 2

fun handleResult(title: String, message: String) {
    Log.i(title, message)
}

fun apiRequestError(`while`: String) {
    throw Throwable("Conneciton error while $`while`")
}