package com.example.innovateapprecruitmenttest.viewmodel

import android.os.Build
import android.util.Log
import android.util.TimeFormatException
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.innovateapprecruitmenttest.model.TodoListItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddEditTodoViewModel: ViewModel() {
    val todoLiveData = MutableLiveData<TodoListItem>()
    val saveLiveData = MutableLiveData<Boolean>()

    var title = ObservableField<String>("")
    var description = ObservableField<String>("")
    var deadline = ObservableField<Long>()
    var priority = false

    var todo: TodoListItem? = null


    fun saveTodo() {
        Log.i("Save button", canSaveTodo().toString())
        return if (canSaveTodo()) {
            val newTodo = TodoListItem(
                id = todo?.id ?: "new_item",
                title = title.get()!!,
                description = description.get()!!,
                priority = priority,
                deadlineAt = deadline.get()!!
            )
            todoLiveData.postValue(newTodo)
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

    fun initTodo(oldTodo: TodoListItem) {
        this.todo = oldTodo
        title.set(oldTodo.title)
        description.set(oldTodo.description)
        priority = oldTodo.priority
        deadline.set(oldTodo.deadlineAt)
    }
}