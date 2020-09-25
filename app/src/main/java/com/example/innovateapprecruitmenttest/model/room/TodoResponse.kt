package com.example.innovateapprecruitmenttest.model.room

import com.example.innovateapprecruitmenttest.model.RawTodo
import com.squareup.moshi.Json

class TodoResponse(
    @Json(name = "results") val todos: List<RawTodo>?
)