package com.example.innovateapprecruitmenttest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepository
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.utils.handleResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class AllTodosViewModel(
    private val todoRepository: TodoRepository,
    val todosLiveData: MutableLiveData<List<TodoListItem>>
): ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun updateTodo(todo: TodoListItem) {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching { todoRepository.updateTodo(todo) }
                .onSuccess {
                    handleResult("Updating todo result", "Successfully updated todo")
                }.onFailure {
                    handleResult("Updating todo result", "Error updating todo")
                }
        }
    }

    fun addTodo(todo: TodoListItem) {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching { todoRepository }
        }
    }

    fun restoreToddos() {
        Log.i("Restored todos", todosLiveData.value.toString()) // just to check if it actually works
    }

    fun initTodos(list: List<TodoListItem>) {
        todosLiveData.postValue(list)
    }
}