package com.example.innovateapprecruitmenttest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepository
import com.example.innovateapprecruitmenttest.model.RawTodo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val todoRepository: TodoRepository): ViewModel() {
    val todosLiveData = MutableLiveData<List<RawTodo>>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getTodos() {
        viewModelScope.launch(coroutineExceptionHandler) {
            delay(500)
            val result = kotlin.runCatching { todoRepository.getTodos() }

            result.onSuccess { todos ->
                todosLiveData.postValue(todos)
            }.onFailure { error ->
                handleError(error)
            }
        }
    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
    }
}