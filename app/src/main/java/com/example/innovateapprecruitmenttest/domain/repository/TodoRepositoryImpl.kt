package com.example.innovateapprecruitmenttest.domain.repository

import android.util.Log
import com.example.innovateapprecruitmenttest.di.API_KEY
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.model.room.TodoDao
import com.example.innovateapprecruitmenttest.utils.apiRequestError
import com.example.innovateapprecruitmenttest.utils.formatTo
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


// Injected in AppModule
class TodoRepositoryImpl(
    private val todoApi: TodoAPI,
    private val todoDao: TodoDao
): TodoRepository {

    override suspend fun getTodos(): List<TodoListItem> {
//         If there's no internet connection, default to the cached values.
//         Otherwise propagate the error.
        val cachedTodos = todoDao.getSavedTodos()
        val apiTodos = try { // apiTodo returns RawTodo, so we must transform it to TodoListItem as follows:
            todoApi.getAllTodos(API_KEY).todos.map {
                Log.i("Todo deadline", it.deadlineAt.toString())
                val parsedDate = LocalDateTime.parse(it.deadlineAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                val zonedTime = parsedDate.atZone(ZoneId.of("Europe/Warsaw"))
                val milis = zonedTime.toInstant().toEpochMilli()
                TodoListItem(
                    it.id,
                    it.title,
                    it.description,
                    it.priority,
                    milis
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
            val apiIdResult = apiRequest.body()!!.id
            val tempTodo = TodoListItem(apiIdResult, "", null, false, null)
            todoDao.insertTodo(tempTodo)
            Log.i("Inserted todo id:", apiIdResult)
            if (apiRequest.isSuccessful) {
                val updatedTodo = updateTodo(apiIdResult, todo)
                if (updatedTodo != null) {
                    Log.i("Inserting todo result:", "Success:::${updatedTodo.toString()}")
                    updatedTodo
                } else {
                    deleteTodo(tempTodo) // only id matters
                    Log.i("Inserting todo result:", "Deleted todo id:::$apiIdResult}")
                    null
                }
            } else {
                Log.i("Inserting todo result", apiRequest.errorBody()?.string().toString())
                apiRequestError("inserting")
                null
            }
        } catch (error: Throwable) {
            Log.i("Inserting todo result:", "Error:::${error.message}")
            null
        }

    override suspend fun updateTodo(id: String, todo: TodoListItem): TodoListItem? =
        try {
            val params = HashMap<String, Any>()
            params["title"] = todo.title
            params["priority"] = todo.priority
            todo.description?.let { params["description"] = todo.description }
            todo.deadlineAt?.let {
                val date = Date(todo.deadlineAt).formatTo("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                params["deadline_at"] = date
            }
            val apiRequest = todoApi.updateTodo(API_KEY, id, params)
            if (apiRequest.isSuccessful) {
                val updatedTodo = TodoListItem(
                    id,
                    todo.title,
                    todo.description,
                    todo.priority,
                    todo.deadlineAt
                )
                todoDao.updateTodo(updatedTodo)
                updatedTodo
            } else {
                Log.i("Error while updating", apiRequest.errorBody()?.string().toString())
                apiRequestError("updating")
                null
            }
        } catch (error: Throwable) {
            Log.i("Updating todo result:", "Error:::${error.message}")
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
}