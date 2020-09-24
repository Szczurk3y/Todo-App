package com.example.innovateapprecruitmenttest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovateapprecruitmenttest.di.API_KEY
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class AllTodosViewModel: ViewModel(), KoinComponent {
    val disposables = CompositeDisposable()
    val todoApi: TodoAPI by inject()

    fun getTodos() {
        disposables.add(todoApi.getAllTodos(API_KEY)
            .filter {
                (200..300).contains(it.code())
            }
            .map { it.body() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.i("downloaded todos:", it?.todos.toString()) },
                { error -> println("Error ::: ${error.message}") }
            )
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}