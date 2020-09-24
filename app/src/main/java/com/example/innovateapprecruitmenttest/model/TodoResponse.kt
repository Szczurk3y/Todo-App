package com.example.innovateapprecruitmenttest.model

import com.squareup.moshi.Json
import io.reactivex.Observable

class TodoResponse(
    @Json(name = "results") val todos: List<RawTodo>?
)