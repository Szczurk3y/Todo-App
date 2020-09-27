package com.example.innovateapprecruitmenttest.view.content

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.adapter.TodosAdapter
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.utils.ADD_REQUEST_CODE
import com.example.innovateapprecruitmenttest.utils.EDIT_REQUEST_CODE
import com.example.innovateapprecruitmenttest.viewmodel.AllTodosViewModel
import kotlinx.android.synthetic.main.activity_alltodos.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class AllTodosActivity: AppCompatActivity(R.layout.activity_alltodos), KoinComponent {
    private val viewmodel: AllTodosViewModel by viewModel<AllTodosViewModel>() // Injecting viewmodel
    lateinit var adapter: TodosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))

        val splashLoadedTodos = intent.getParcelableArrayListExtra<RawTodo>(RawTodo.TODO_KEY)?.toMutableList()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TodosAdapter(onClick = { todo, position, view ->
                Log.i("Todo clicked:", todo.title)
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(RawTodo.TODO_KEY, todo)
                startActivityForResult(intent, EDIT_REQUEST_CODE)
            })

        recyclerView.adapter = adapter

        viewmodel.todosLiveData.observe(this, Observer { todos ->
            adapter.submitList(todos)
        })

        viewmodel.initTodos(splashLoadedTodos)

        fab.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.i("Edit request", Activity.RESULT_OK.toString())
            when(requestCode) {
                EDIT_REQUEST_CODE -> {
                    val updated_todo = data?.getParcelableExtra<RawTodo>(RawTodo.TODO_KEY)
                    updated_todo?.let {
                        viewmodel.updateTodo(updated_todo)
                    }
                }

                ADD_REQUEST_CODE -> {

                }
            }
        } else {
            Log.i("Edit request", resultCode.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}