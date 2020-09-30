package com.example.innovateapprecruitmenttest.view.content

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.content_addedittodo.*

class AddEditTodoActivity: AppCompatActivity(R.layout.activity_addedittodo) {

    private val viewmodel: AddEditTodoViewModel by viewModel<AddEditTodoViewModel>() // Injecting viewmodel
    lateinit var binding: ContentAddedittodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.content_addedittodo)
        binding.viewmodel = viewmodel

        val todoToUpdate = intent.getParcelableExtra<TodoListItem>(RawTodo.TODO_KEY)

        if (todoToUpdate != null) {
            et_title.hint = todoToUpdate.title
            et_description.hint = todoToUpdate.description
            // TODO: set date to deadline
            calendar.setDate(10000, true, false)
            viewmodel.initTodo(todoToUpdate)
            Log.i("Date:", calendar.date.toString())
        }

        viewmodel.todoLiveData.observe(this, Observer {
            updateTodoAndReturn(it)
        })

        viewmodel.saveLiveData.observe(this, Observer { canSave ->
            if (!canSave) {
                Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show()
            }
            // We can't say if it's saved there, because connection errors may occur later (Toast saved is displayed in AllTodosActivity)
        })
    }

    private fun updateTodoAndReturn(todo: TodoListItem) {
        val data = Intent()
        data.putExtra(RawTodo.TODO_KEY, todo)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}