package com.example.innovateapprecruitmenttest.model.room

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? = dateLong?.let { Date(dateLong) }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.let { date.time }

}