package com.example.innovateapprecruitmenttest.model.room

import androidx.room.*
import com.example.innovateapprecruitmenttest.model.TodoListItem

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos(todos: List<TodoListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoListItem)

    @Delete
    suspend fun deleteTodo(todo: TodoListItem)

    @Update
    suspend fun updateTodo(todo: TodoListItem)

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getTodo(id: String): TodoListItem

    @Query("SELECT * FROM todo_table")
    suspend fun getSavedTodos(): List<TodoListItem>
}