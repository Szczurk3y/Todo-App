package com.example.innovateapprecruitmenttest.domain.repository

import androidx.lifecycle.LiveData
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.model.room.TodoDao
import org.koin.core.KoinComponent
import org.koin.core.inject

// Inject in AppModule
class TodoRepositoryImpl(
    private val todoApi: TodoAPI,
    private val todoDao: TodoDao
): TodoRepository {

    override fun getAllTodos(): LiveData<List<RawTodo>> {
        /**
         * If there's no internet connection, default to the cached values.
         * Otherwise propagate the error.
         * */
        TODO()
    }

    override fun getTodo(): LiveData<RawTodo> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodo() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo() {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo() {
        TODO("Not yet implemented")
    }
}