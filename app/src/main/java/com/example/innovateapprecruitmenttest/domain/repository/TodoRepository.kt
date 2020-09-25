package com.example.innovateapprecruitmenttest.domain.repository

import androidx.lifecycle.LiveData
import com.example.innovateapprecruitmenttest.model.RawTodo

interface TodoRepository {
    suspend fun getTodos(): List<RawTodo>
    suspend fun getTodo(id: String): RawTodo
    suspend fun insertAllTodos()
    suspend fun deleteTodo()
    suspend fun updateTodo()
}