package com.example.innovateapprecruitmenttest.view.content

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.adapter.TodosAdapter
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.utils.ADD_REQUEST_CODE
import com.example.innovateapprecruitmenttest.utils.EDIT_REQUEST_CODE
import com.example.innovateapprecruitmenttest.viewmodel.AllTodosViewModel
import kotlinx.android.synthetic.main.activity_alltodos.*
import kotlinx.android.synthetic.main.content_alltodos.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class AllTodosActivity: AppCompatActivity(R.layout.activity_alltodos), KoinComponent {
    private val viewmodel: AllTodosViewModel by viewModel<AllTodosViewModel>() // Injecting viewmodel
    lateinit var adapter: TodosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))

        val splashLoadedTodos = intent.getParcelableArrayListExtra<TodoListItem>(RawTodo.TODO_KEY)?.toMutableList()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodosAdapter(
            onClick = { todo, position, view ->
                Log.i("Todo clicked:", todo.title)
                val intent = Intent(this, AddEditTodoActivity::class.java)
                intent.putExtra(RawTodo.TODO_KEY, todo)
                startActivityForResult(intent, EDIT_REQUEST_CODE)
            })

        recyclerView.adapter = adapter

        viewmodel.todosLiveData.observe(this, Observer { todos ->
            adapter.submitList(todos)
            adapter.notifyDataSetChanged()
            Log.i("Adapter list", adapter.currentList.toString())
        })

        viewmodel.saveLiveData.observe(this, Observer { saved ->
            if (saved) {
                Toast.makeText(this, getString(R.string.successfully_saving_todo), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.error_saving_todo), Toast.LENGTH_SHORT).show()
            }
        })

        if (!splashLoadedTodos.isNullOrEmpty()) {
            viewmodel.initTodos(splashLoadedTodos)
        } else {
            viewmodel.restoreTodos()
        }

        fab.setOnClickListener {
            val intent = Intent(this, AddEditTodoActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_CODE)
        }

        initTouchHelper()
    }

    private fun initTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewmodel.deleteTodo(adapter.getTodoAt(viewHolder.adapterPosition), viewHolder.adapterPosition)
                Toast.makeText(this@AllTodosActivity, "Todo deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.i("Activity request result", Activity.RESULT_OK.toString())
            when(requestCode) {
                EDIT_REQUEST_CODE -> {
                    val updatedTodo = data?.getParcelableExtra<TodoListItem>(RawTodo.TODO_KEY)
                    updatedTodo?.let {
                        Log.i("Editing todo result", it.toString())
                        viewmodel.updateTodo(updatedTodo)
                    }
                }

                ADD_REQUEST_CODE -> {
                    val newTodo = data?.getParcelableExtra<TodoListItem>(RawTodo.TODO_KEY)
                    newTodo?.let {
                        Log.i("Adding todo result", it.toString())
                        viewmodel.insertTodo(it)
                    }
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