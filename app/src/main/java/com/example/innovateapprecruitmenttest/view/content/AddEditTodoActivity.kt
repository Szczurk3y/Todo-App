package com.example.innovateapprecruitmenttest.view.content

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.viewmodel.AddEditTodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.innovateapprecruitmenttest.databinding.ContentAddedittodoBinding

class AddEditTodoActivity: AppCompatActivity(R.layout.activity_addedittodo) {

    private val viewmodel: AddEditTodoViewModel by viewModel<AddEditTodoViewModel>() // Injecting viewmodel
    lateinit var binding: ContentAddedittodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.content_addedittodo)
        binding.viewmodel = viewmodel

        val todo = intent.getParcelableExtra<TodoListItem>(RawTodo.TODO_KEY)
        if (todo != null) {
            viewmodel.init(todo)
        }

        viewmodel.todoLiveData.observe(this, Observer {
            updateTodoAndReturn(it)
        })

        viewmodel.saveLiveData.observe(this, Observer { canSave ->
            if (canSave) {
                Toast.makeText(this, getString(R.string.successfully_saving_todo), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.error_saving_todo), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTodoAndReturn(todo: TodoListItem) {
        val data = Intent()
        data.putExtra(RawTodo.TODO_KEY, todo)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}