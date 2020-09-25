package com.example.innovateapprecruitmenttest.domain.repository

import androidx.lifecycle.LiveData
import com.example.innovateapprecruitmenttest.model.RawTodo

interface TodoRepository {
    suspend fun getAllTodos(): LiveData<List<RawTodo>>
    suspend fun getTodo(): LiveData<RawTodo>
    suspend fun insertTodo()
    suspend fun deleteTodo()
    suspend fun updateTodo()
}