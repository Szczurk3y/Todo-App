package com.example.innovateapprecruitmenttest.model

import com.squareup.moshi.Json

class TodoResponse(
    @Json(name = "results") val todos: List<RawTodo>
)