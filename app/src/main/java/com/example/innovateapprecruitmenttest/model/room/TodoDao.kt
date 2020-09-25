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
    fun getTodo(id: String): LiveData<RawTodo> // returning type of LiveData cannot be suspend function

    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): LiveData<List<RawTodo>> // returning type of LiveData cannot be suspend function
}