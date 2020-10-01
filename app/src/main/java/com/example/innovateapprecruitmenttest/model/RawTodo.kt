package com.example.innovateapprecruitmenttest.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class RawTodo (
    val id: String,
    val title: String,
    val description: String?,
    @Json(name = "deadline_at") val deadlineAt: String?,
    val completed: Boolean,
    @Json(name = "completed_at") val completedAt: String?,
    val priority: Boolean,
    @Json(name = "inserted_at") val insertedAt: String,
    @Json(name = "updated_at") val updatedAt: String
): Parcelable {
    companion object {
        const val TODO_KEY = "TODO_KEY"
    }
}