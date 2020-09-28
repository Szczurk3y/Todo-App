package com.example.innovateapprecruitmenttest.domain.repository

import android.util.Log
import com.example.innovateapprecruitmenttest.di.API_KEY
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.model.room.TodoDao
import com.example.innovateapprecruitmenttest.utils.handleResult
import io.reactivex.disposables.CompositeDisposable


// Injected in AppModule
class TodoRepositoryImpl(
    private val todoApi: TodoAPI,
    private val todoDao: TodoDao
): TodoRepository {

    val disposables = CompositeDisposable()

    override suspend fun getTodos(): List<TodoListItem> {
        /**
         * If there's no internet connection, default to the cached values.
         * Otherwise propagate the error.
         * */
        val cachedTodos = todoDao.getSavedTodos()
        val apiTodos = try { // apiTodo returns RawTodo, so we must transform it to TodoListItem as follows:
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

    override suspend fun insertTodo(todo: TodoListItem): TodoListItem? =
        try {
            val apiIdResult = todoApi.insertTodo(API_KEY, todo.title).id
            todoDao.insertTodo(todo)
            TodoListItem(
                apiIdResult,
                todo.title,
                todo.description,
                todo.priority,
                todo.deadlineAt
            )
        } catch (error: Throwable) {
            Log.i("Inserting todo result:", "Error:::${error.message}")
            null
        }


    override suspend fun deleteTodo(todo: TodoListItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo(todo: TodoListItem) {
        todoDao.updateTodo(todo)
    }


}