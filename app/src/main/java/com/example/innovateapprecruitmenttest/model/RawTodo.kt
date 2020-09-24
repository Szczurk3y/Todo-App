package com.example.innovateapprecruitmenttest.model

import com.squareup.moshi.Json
import java.util.*

data class RawTodo(
    val id: String,
    val title: String,
    val description: String?,
    @Json(name = "deadline_at") val deadlineAt: String?,
    val completed: Boolean,
    @Json(name = "completed_at") val completedAt: String?,
    val priority: Boolean,
    @Json(name = "inserted_at") val insertedAt: String,
    @Json(name = "updated_at") val updatedAt: String
)