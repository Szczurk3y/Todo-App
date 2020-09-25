package com.example.innovateapprecruitmenttest.di

import com.example.innovateapprecruitmenttest.viewmodel.AllTodosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Creates viewmodels for the app
 */

fun viewModelModule() = module {
    viewModel { AllTodosViewModel(get()) }
}