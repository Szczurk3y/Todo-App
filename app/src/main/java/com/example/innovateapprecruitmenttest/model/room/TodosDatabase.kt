package com.example.innovateapprecruitmenttest.model.room

import android.content.Context
import androidx.room.*
import com.example.innovateapprecruitmenttest.model.TodoListItem

@Database(entities = [TodoListItem::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TodosDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        fun create(context: Context): TodosDatabase {
            return Room.databaseBuilder(
                context,
                TodosDatabase::class.java,
                "todos"
            ).build()
        }
    }
}