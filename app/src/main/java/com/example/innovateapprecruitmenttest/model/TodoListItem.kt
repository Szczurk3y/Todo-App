package com.example.innovateapprecruitmenttest.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "todo_table")
@Parcelize
data class TodoListItem (
    @PrimaryKey @NonNull val id: String,
    val title: String,
    val description: String?,
    val priority: Boolean,
    @Json(name = "deadline_at") val deadlineAt: Long?
): Parcelable