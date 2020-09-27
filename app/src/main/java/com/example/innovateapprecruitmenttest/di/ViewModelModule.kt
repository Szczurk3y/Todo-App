package com.example.innovateapprecruitmenttest.di

import com.example.innovateapprecruitmenttest.viewmodel.AllTodosViewModel
import com.example.innovateapprecruitmenttest.viewmodel.DetailsViewModel
import com.example.innovateapprecruitmenttest.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Creates viewmodels for the app
 */

fun viewModelModule() = module {
    viewModel { SplashViewModel(get()) }
    viewModel { AllTodosViewModel(get()) }
    viewModel { DetailsViewModel() }
}