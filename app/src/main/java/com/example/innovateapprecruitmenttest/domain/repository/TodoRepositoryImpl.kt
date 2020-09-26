package com.example.innovateapprecruitmenttest.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.innovateapprecruitmenttest.di.API_KEY
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.model.room.TodoDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

// Injected in AppModule
class TodoRepositoryImpl(
    private val todoApi: TodoAPI,
    private val todoDao: TodoDao
): TodoRepository {

    override suspend fun getTodos(): List<RawTodo> {
        /**
         * If there's no internet connection, default to the cached values.
         * Otherwise propagate the error.
         * */
        val cachedTodos = todoDao.getSavedTodos()
        val apiTodos = try {
            todoApi.getAllTodos(API_KEY).todos
        } catch (error: Throwable) {
            null
        }
        if (apiTodos != null) {
            todoDao.insertAllTodos(apiTodos)
        }

        return apiTodos ?: cachedTodos
    }

    override suspend fun getTodo(id: String): RawTodo {
        TODO("Not yet implemented")
    }

    override suspend fun insertAllTodos() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo(todo: RawTodo) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo(todo: RawTodo) {
        todoDao.updateTodo(todo)
    }
}