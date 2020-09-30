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

    val saveLiveData = MutableLiveData<Boolean>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun updateTodo(todo: TodoListItem) {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching { todoRepository.updateTodo(todo.id, todo) }
                .onSuccess { updatedTodo: TodoListItem? ->
                    updatedTodo?.let {
                        val pos = todosLiveData.value?.map { it.id }?.indexOf(it.id)
                        Log.i("Item position:", pos.toString())
                        pos?.let { todosLiveData.updateItemAt(pos, updatedTodo) }
                    }
                    Log.i("Updating todo result", "Successfully updated todo")
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
                        saveLiveData.postValue(true)
                    }
                }.onFailure { error ->
                    Log.i("Inserting todo result:", "Error:::${error.message}")
                    saveLiveData.postValue(false)
                }
        }
    }

    fun deleteTodo(todo: TodoListItem, position: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching { todoRepository.deleteTodo(todo) }
                .onSuccess {
                    todosLiveData.deleteItemAt(position)
                    Log.i("Deleting todo result:", "Success, deleted todo id ::: ${todo.id} ")
                }.onFailure { error ->
                    Log.i("Deleting todo result:", "Error:::${error.message}")
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

    fun <T> MutableLiveData<MutableList<T>>.deleteItemAt(position: Int) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.removeAt(position)
        this.value = oldValue
    }

    fun <T> MutableLiveData<MutableList<T>>.updateItemAt(position: Int, item: T) {
        val oldValue = this.value ?: mutableListOf()
        oldValue.set(position, item)
        this.value = oldValue
    }
}