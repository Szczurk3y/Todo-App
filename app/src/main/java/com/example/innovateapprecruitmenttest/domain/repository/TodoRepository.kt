package com.example.innovateapprecruitmenttest.domain.repository

import androidx.lifecycle.LiveData
import com.example.innovateapprecruitmenttest.model.RawTodo

interface TodoRepository {
    suspend fun insertAllTodos()
    suspend fun deleteTodo(todo: RawTodo)
    suspend fun updateTodo(todo: RawTodo)
    suspend fun getTodo(id: String): RawTodo
    suspend fun getTodos(): List<RawTodo>
}