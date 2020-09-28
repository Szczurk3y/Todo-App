package com.example.innovateapprecruitmenttest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepository
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.utils.handleResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class AllTodosViewModel(
    private val todoRepository: TodoRepository,
    val todosLiveData: MutableLiveData<MutableList<TodoListItem>>
): ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun updateTodo(todo: TodoListItem) {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching { todoRepository.updateTodo(todo) }
                .onSuccess {
                    handleResult("Updating todo result", "Successfully updated todo")
                }.onFailure {error ->
                    handleResult("Updating todo result", "Error:::${error.message}")
                }
        }
    }

    fun insertTodo(todo: TodoListItem) {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching { todoRepository.insertTodo(todo) }
                .onSuccess { newTodo: TodoListItem? ->
                    newTodo?.let {
                        todosLiveData.addNewItem(newTodo)
                        Log.i("Inserting todo result:", "Success, todo id ::: $it")
                    }
                }.onFailure { error ->
                    Log.i("Inserting todo result:", "Error:::${error.message}")
                }
        }
    }

    fun restoreTodos() {
        Log.i("Restored todos", todosLiveData.value.toString()) // just to check if it actually works
    }

    fun initTodos(list: MutableList<TodoListItem>) {
        todosLiveData.postValue(list)
    }

    fun <T> MutableLiveData<MutableList<T>>.addNewItem(item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.add(item)
        this.value = oldValue
    }
}