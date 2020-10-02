package com.example.innovateapprecruitmenttest.di

import androidx.lifecycle.MutableLiveData
import com.example.innovateapprecruitmenttest.model.TodoListItem
import com.example.innovateapprecruitmenttest.viewmodel.AllTodosViewModel
import com.example.innovateapprecruitmenttest.viewmodel.AddEditTodoViewModel
import com.example.innovateapprecruitmenttest.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


//  Creates viewmodels for the app
fun viewModelModule() = module {
    viewModel { SplashViewModel(get()) }
    viewModel { AllTodosViewModel(get(), get(), get()) }
    viewModel { AddEditTodoViewModel() }
}