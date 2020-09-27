package com.example.innovateapprecruitmenttest.view.content

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.viewmodel.AddEditTodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEditTodoActivity: AppCompatActivity(R.layout.activity_addedittodo) {

    private val viewmodel: AddEditTodoViewModel by viewModel<AddEditTodoViewModel>() // Injecting viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todo = intent.getParcelableExtra<TodoListItem>(RawTodo.TODO_KEY)
        if (todo != null) {
            viewmodel.init(todo)
        }

        viewmodel.todoLiveData.observe(this, Observer {
            updateTodoAndReturn(it)
        })
    }

    private fun updateTodoAndReturn(todo: TodoListItem) {
        val data = Intent()
        data.putExtra(RawTodo.TODO_KEY, todo)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}