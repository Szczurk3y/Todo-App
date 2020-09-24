package com.example.innovateapprecruitmenttest.domain.repository

import com.example.innovateapprecruitmenttest.model.RawTodo
import io.reactivex.Observable

interface TodoRepository {
    suspend fun getAllTodos(): Observable<List<RawTodo>>
    suspend fun insertTodo()
    suspend fun deleteTodo()
    suspend fun updateTodo()
}