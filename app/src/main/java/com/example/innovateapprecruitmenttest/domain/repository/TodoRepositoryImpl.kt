package com.example.innovateapprecruitmenttest.domain.repository

import android.util.Log
import com.example.innovateapprecruitmenttest.di.API_KEY
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.model.room.TodoDao
import com.example.innovateapprecruitmenttest.utils.apiRequestError
import com.example.innovateapprecruitmenttest.utils.handleResult
import io.reactivex.disposables.CompositeDisposable


// Injected in AppModule
class TodoRepositoryImpl(
    private val todoApi: TodoAPI,
    private val todoDao: TodoDao
): TodoRepository {

    val disposables = CompositeDisposable()

    override suspend fun getTodos(): List<TodoListItem> {

//         If there's no internet connection, default to the cached values.
//         Otherwise propagate the error.

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
            val apiRequest = todoApi.insertTodo(API_KEY, todo.title)
            if (apiRequest.isSuccessful) {
                val apiIdResult = apiRequest.body()!!.id
                val updatedTodo = updateTodo(apiIdResult, todo)
                Log.i("Updating todo result:", "Success:::${updatedTodo.toString()}")
                if (updatedTodo != null) {
                    todoDao.insertTodo(updatedTodo)
                    updatedTodo
                } else {
                    null
                }
            } else {
                apiRequestError("inserting")
                null
            }
        } catch (error: Throwable) {
            Log.i("Inserting todo result:", "Error:::${error.message}")
            null
        }


    override suspend fun deleteTodo(todo: TodoListItem): Boolean = try {
        val apiRequest = todoApi.deleteTodo(API_KEY, todo.id)
        if (apiRequest.isSuccessful) {
            todoDao.deleteTodo(todo)
            Log.i("Deleting todo result:", "Success:::deleted ${todo.id}")
            true
        } else {
            apiRequestError("deleting")
            false
        }
    } catch (error: Throwable) {
        Log.i("Deleting todo result:", "Error:::${error.message}")
        false
    }

    override suspend fun updateTodo(id: String, todo: TodoListItem): TodoListItem? =
        try {
            val params = HashMap<String, Any>()
            params["title"] = todo.title
            params["priority"] = todo.priority
            todo.description?.let { params["description"] = todo.description }
            todo.deadlineAt?.let { params["deadline_at"] = todo.deadlineAt }
            val apiRequest = todoApi.updateTodo(API_KEY, id, params)
            if (apiRequest.isSuccessful) {
                TodoListItem(
                    id,
                    todo.title,
                    todo.description,
                    todo.priority,
                    todo.deadlineAt
                )
            } else {
                apiRequestError("updating")
                null
            }
        } catch (error: Throwable) {
            Log.i("Updating todo result:", "Error:::${error.message}")
            null
        }


}