package com.example.innovateapprecruitmenttest.model.room

import android.content.Context
import android.content.res.Resources
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.innovateapprecruitmenttest.model.RawTodo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [RawTodo::class], version = 1, exportSchema = false)
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