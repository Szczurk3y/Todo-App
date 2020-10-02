package com.example.innovateapprecruitmenttest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovateapprecruitmenttest.di.API_KEY
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepository
import com.example.innovateapprecruitmenttest.model.OrderBy
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.utils.addNewItem
import com.example.innovateapprecruitmenttest.utils.deleteItemAt
import com.example.innovateapprecruitmenttest.utils.handleResult
import com.example.innovateapprecruitmenttest.utils.updateItemAt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AllTodosViewModel(
    private val todoRepository: TodoRepository,
    private val todoApi: TodoAPI,
    val todosLiveData: MutableLiveData<MutableList<TodoListItem>>
): ViewModel() {

    val saveLiveData = MutableLiveData<Boolean>()
    val disposables = CompositeDisposable()

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
                        pos?.let {
                            todosLiveData.updateItemAt(pos, updatedTodo)
                            saveLiveData.postValue(true)
                        }
                    }
                    Log.i("Updating todo result", "Successfully updated todo")
                }.onFailure {error ->
                    saveLiveData.postValue(false)
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

    fun orderTodosBy(parameter: String) {
        val queryMap = HashMap<String, String>()
        queryMap["order_by"] = parameter
        val orderedTodos = todoApi.orderTodosBy(API_KEY, queryMap)
            .filter {
                (200..300).contains(it.code())
            }
            .map {
                it.body()?.todos?.map { rawtodo ->
                    val parsedDate = LocalDateTime.parse(rawtodo.deadlineAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    val zonedTime = parsedDate.atZone(ZoneId.of("Europe/Warsaw"))
                    val milis = zonedTime.toInstant().toEpochMilli()
                    TodoListItem(
                        rawtodo.id,
                        rawtodo.title,
                        rawtodo.description,
                        rawtodo.priority,
                        milis
                    )
                }
            }
        disposables.add(orderedTodos.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { todos ->
                todosLiveData.value = todos?.toMutableList()
            })
    }

    fun initTodos(list: MutableList<TodoListItem>) {
        todosLiveData.postValue(list)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}