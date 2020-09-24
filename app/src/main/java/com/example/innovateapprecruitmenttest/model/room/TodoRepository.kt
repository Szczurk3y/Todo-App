package com.example.innovateapprecruitmenttest.model.room

import androidx.lifecycle.LiveData
import com.example.innovateapprecruitmenttest.model.RawTodo

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): LiveData<List<RawTodo>> {
        return todoDao.getAllTodos()
    }

    fun getTodo(id: String): LiveData<RawTodo> {
        return todoDao.getTodo(id)
    }

    fun updateTodo(todo: RawTodo) {
        todoDao.updateTodo(todo)
    }

    fun deleteTodo(todo: RawTodo) {
        todoDao.deleteTodo(todo)
    }
}