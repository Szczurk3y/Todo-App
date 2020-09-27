package com.example.innovateapprecruitmenttest.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepository
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.TodoListItem

class AddEditTodoViewModel: ViewModel() {
    val todoLiveData = MutableLiveData<TodoListItem>()


    fun saveDetails() {

//        todoLiveData.postValue(updatedTodo)
    }

    fun init(todo: TodoListItem) {

    }
}