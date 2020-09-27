package com.example.innovateapprecruitmenttest.view.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.content_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity: AppCompatActivity(R.layout.activity_details) {

    private val viewmodel: DetailsViewModel by viewModel<DetailsViewModel>() // Injecting viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todo = intent.getParcelableExtra<RawTodo>(RawTodo.TODO_KEY)
        if (todo != null) {
            viewmodel.init(todo)
        }

        viewmodel.todoLiveData.observe(this, Observer {
            updateTodoAndReturn(it)
        })
    }

    private fun updateTodoAndReturn(todo: RawTodo) {
        val data = Intent()
        data.putExtra(RawTodo.TODO_KEY, todo)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}