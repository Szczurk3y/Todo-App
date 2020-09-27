package com.example.innovateapprecruitmenttest.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepository
import com.example.innovateapprecruitmenttest.model.RawTodo

class DetailsViewModel: ViewModel() {
    val todoLiveData = MutableLiveData<RawTodo>()

    lateinit var updatedTodo: RawTodo
    lateinit var id: String
    lateinit var deadlineText: ObservableField<String>
    lateinit var titleText: ObservableField<String>
    lateinit var descriptionText: ObservableField<String>
    lateinit var completedAtText: ObservableField<String>
    lateinit var priority: ObservableField<Boolean>
    lateinit var insertedAt: ObservableField<String>
    lateinit var updatedAt: ObservableField<String>
    lateinit var completed: ObservableField<Boolean>

    fun saveDetails() {
        updatedTodo = RawTodo(
            id,
            titleText.get() ?: "",
            descriptionText.get(),
            deadlineText.get(),
            false,
            completedAtText.get(),
            false,
            insertedAt.get() ?: "",
            updatedAt.get() ?: ""
        )
        Log.i("SAVE todo", updatedTodo.toString())
        todoLiveData.postValue(updatedTodo)
    }

    fun init(todo: RawTodo) {
        id = todo.id
        deadlineText = ObservableField(todo.deadlineAt ?: "")
        titleText = ObservableField(todo.title)
        descriptionText = ObservableField(todo.description ?: "")
        completedAtText = ObservableField(todo.completedAt ?: "")
        priority = ObservableField(todo.priority)
        insertedAt = ObservableField(todo.insertedAt)
        updatedAt = ObservableField(todo.updatedAt)
        completed = ObservableField(todo.completed)
    }
}