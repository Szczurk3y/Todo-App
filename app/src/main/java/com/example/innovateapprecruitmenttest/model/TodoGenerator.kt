package com.example.innovateapprecruitmenttest.model

class TodoGenerator {
    // Actually it makes code easier to test
    fun generateTodo(id: String, title: String, description: String, priority: Boolean, deadline: String): TodoListItem {
        return TodoListItem(id, title, description, priority, deadline)
    }
}