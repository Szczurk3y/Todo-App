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
            val tvDeadline: TextView = findViewById(R.id.tv_item_deadline)
            val tvTitle: TextView = findViewById(R.id.tv_item_title)
            val tvDescription: TextView = findViewById(R.id.tv_item_description)

            tvDeadline.text = item.deadlineAt.toString() ?: "Add deadline"
            tvTitle.text = item.title
            tvDescription.text = item.description ?: "Add description"

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