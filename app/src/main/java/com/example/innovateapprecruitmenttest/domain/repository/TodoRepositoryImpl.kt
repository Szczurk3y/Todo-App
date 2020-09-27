package com.example.innovateapprecruitmenttest.domain.repository

import com.example.innovateapprecruitmenttest.di.API_KEY
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.model.room.TodoDao


// Injected in AppModule
class TodoRepositoryImpl(
    private val todoApi: TodoAPI,
    private val todoDao: TodoDao
): TodoRepository {

    override suspend fun getTodos(): List<TodoListItem> {
        /**
         * If there's no internet connection, default to the cached values.
         * Otherwise propagate the error.
         * */
        val cachedTodos = todoDao.getSavedTodos()
        val apiTodos = try { // apiTodos returns RawTodo, so we must transform it to TodoListItem as follows:
            todoApi.getAllTodos(API_KEY).todos.map {
                TodoListItem(
                    it.id,
                    it.title,
                    it.description,
                    it.priority,
                    it.deadlineAt
                )
            }
        } catch (error: Throwable) {
            null
        }
        if (apiTodos != null) {
            todoDao.insertAllTodos(apiTodos)
        }

        return apiTodos ?: cachedTodos
    }

    override suspend fun getTodo(id: String): TodoListItem {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllTodos() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo(todo: TodoListItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo(todo: TodoListItem) {
        todoDao.updateTodo(todo)
    }
}