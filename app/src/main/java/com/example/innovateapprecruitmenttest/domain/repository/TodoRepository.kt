package com.example.innovateapprecruitmenttest.domain.repository

import com.example.innovateapprecruitmenttest.model.TodoListItem

interface TodoRepository {
    suspend fun insertTodo(todo: TodoListItem): TodoListItem?
    suspend fun deleteTodo(todo: TodoListItem)
    suspend fun updateTodo(todo: TodoListItem)
    suspend fun getTodo(id: String): TodoListItem
    suspend fun getTodos(): List<TodoListItem>
}