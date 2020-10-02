package com.example.innovateapprecruitmenttest.di

import androidx.lifecycle.MutableLiveData
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepository
import com.example.innovateapprecruitmenttest.domain.repository.TodoRepositoryImpl
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.example.innovateapprecruitmenttest.model.room.TodosDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule() = module {
    single { TodosDatabase.create(androidContext()) } // database
    single { get<TodosDatabase>().todoDao() } // dao
    single { TodoRepositoryImpl(get(), get()) as TodoRepository } // repository
    single { MutableLiveData<MutableList<TodoListItem>>() } // All todos
}