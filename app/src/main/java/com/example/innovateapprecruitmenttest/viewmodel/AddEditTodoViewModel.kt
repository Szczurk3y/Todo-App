package com.example.innovateapprecruitmenttest.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.innovateapprecruitmenttest.model.TodoListItem

class AddEditTodoViewModel: ViewModel() {
    val todoLiveData = MutableLiveData<TodoListItem>()
    val saveLiveData = MutableLiveData<Boolean>()

    var title = ObservableField<String>("")
    var description = ObservableField<String>("")
    var priority = false

    lateinit var todo: TodoListItem


    fun saveTodo() {
        Log.i("Save button", canSaveTodo().toString())
        return if (canSaveTodo()) {
            saveLiveData.postValue(true)
            todoLiveData.postValue(
                TodoListItem(
                    "elo",
                    title.get()!!, // Checked in canSaveTodo()
                    description.get()!!, // Same
                    priority,
                    null
                ))
        } else {
            saveLiveData.postValue(false)
        }
    }

    private fun canSaveTodo(): Boolean {
        val title = this.title.get()
        val description = this.description.get()
        if (title != null && description != null) {
            return title.isNotEmpty() && description.isNotEmpty()
        }
        return false
    }

    fun init(todo: TodoListItem) {
        this.todo = todo
    }
}