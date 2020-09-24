package com.example.innovateapprecruitmenttest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.innovateapprecruitmenttest.model.RawTodo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTodos(todos: List<RawTodo>)

    @Delete
    fun deleteTodo(todo: RawTodo)

    @Update
    fun updateTodo(todo: RawTodo)

    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun getTodo(id: String): LiveData<RawTodo>

    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): LiveData<List<RawTodo>>
}