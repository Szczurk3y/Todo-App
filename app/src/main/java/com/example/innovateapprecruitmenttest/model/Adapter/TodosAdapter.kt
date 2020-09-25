package com.example.innovateapprecruitmenttest.model.Adapter

import android.app.LauncherActivity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.innovateapprecruitmenttest.model.RawTodo

class TodosAdapter: ListAdapter<RawTodo, TodosAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RawTodo>() {
            override fun areItemsTheSame(oldItem: RawTodo, newItem: RawTodo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RawTodo, newItem: RawTodo): Boolean =
                oldItem == newItem
        }
    }
}