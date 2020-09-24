package com.example.innovateapprecruitmenttest.model

import com.squareup.moshi.Json

data class TodoListItem (
    val id: String,
    val title: String,
    val description: String?,
    @Json(name = "deadline_at") val deadlineAt: String?,
    val priority: Boolean
)