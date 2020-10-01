package com.example.innovateapprecruitmenttest.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.TodoListItem
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodosAdapter(private val onClick: (item: TodoListItem, position: Int, view: View) -> Unit): ListAdapter<TodoListItem, TodosAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getTodoAt(position: Int): TodoListItem {
        return getItem(position)
    }

    class ViewHolder(itemView: View, private val onClick: (item: TodoListItem, position: Int, view: View) -> Unit): RecyclerView.ViewHolder(itemView) {
        fun bind(item: TodoListItem) = with(itemView) {
            val resourceId = if (item.priority) {
                R.drawable.ic_star
            } else {
                R.drawable.ic_star_border
            }

            iv_priority.setImageResource(resourceId)
            tv_item_deadline.text = if (item.deadlineAt != null) {
                val date = Date(item.deadlineAt)
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                sdf.format(date)
            } else {
                "Add deadline"
            }
            tv_item_title.text = item.title
            tv_item_description.text = item.description ?: "Add description"


            setOnClickListener { onClick(item, adapterPosition, this) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TodoListItem>() {
            override fun areItemsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TodoListItem, newItem: TodoListItem): Boolean =
                oldItem == newItem
        }
    }
}