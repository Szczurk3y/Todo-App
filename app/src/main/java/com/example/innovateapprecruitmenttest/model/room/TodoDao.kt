package com.example.innovateapprecruitmenttest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.innovateapprecruitmenttest.model.RawTodo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos(todos: List<RawTodo>)

    @Delete
    suspend fun deleteTodo(todo: RawTodo)

    @Update
    suspend fun updateTodo(todo: RawTodo)

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getTodo(id: String): RawTodo

    @Query("SELECT * FROM todo_table")
    suspend fun getSavedTodos(): List<RawTodo>
}