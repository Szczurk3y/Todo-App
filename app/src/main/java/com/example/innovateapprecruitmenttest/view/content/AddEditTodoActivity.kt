package com.example.innovateapprecruitmenttest.view.content

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.viewmodel.AddEditTodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.innovateapprecruitmenttest.databinding.ContentAddedittodoBinding
import kotlinx.android.synthetic.main.activity_addedittodo.*
import kotlinx.android.synthetic.main.content_addedittodo.*
import org.koin.core.KoinComponent
import java.util.*

class AddEditTodoActivity: AppCompatActivity(R.layout.activity_addedittodo), KoinComponent {

    private val viewmodel: AddEditTodoViewModel by viewModel<AddEditTodoViewModel>() // Injecting viewmodel
    lateinit var binding: ContentAddedittodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.content_addedittodo)
        binding.viewmodel = viewmodel
        binding.also {
            it.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val instance = Calendar.getInstance()
                instance.set(year, month, dayOfMonth)
                view.date = instance.timeInMillis
                Log.i("Picked date:", view.date.toString())
                viewmodel.deadline.set(view.date)
            }
        }

        val todoToUpdate = intent.getParcelableExtra<TodoListItem>(RawTodo.TODO_KEY)

        if (todoToUpdate != null) {
            et_title.hint = todoToUpdate.title
            et_description.hint = todoToUpdate.description
            todoToUpdate.deadlineAt?.let { calendar.setDate(todoToUpdate.deadlineAt, true, false) }
            viewmodel.initTodo(todoToUpdate)
        } else {
            viewmodel.deadline.set(calendar.date)
            viewmodel.priority.set(false)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_addedit, menu)
        val item = menu?.findItem(R.id.menu_action_priority)
        val checkbox = item?.actionView?.findViewById<CheckBox>(R.id.action_item_checkbox)
        checkbox?.let { checkBox ->
            checkBox.isChecked = viewmodel.priority.get()!!
            item.isChecked = viewmodel.priority.get()!!
            Log.i("Viewmodel checkbox status", viewmodel.priority.get()!!.toString())
            Log.i("Item checkbox status", viewmodel.priority.get()!!.toString())
            item.let {menuItem ->
                checkBox.setOnClickListener {
                    onOptionsItemSelected(menuItem)
                }
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_action_priority -> {
                item.isChecked = !item.isChecked
                viewmodel.priority.set(item.isChecked)
                Log.i("Checkbox status", item.isChecked.toString())
                Log.i("Viewmodel checkbox status", viewmodel.priority.get()!!.toString())
                true
            }
            else -> false
        }
    }
}