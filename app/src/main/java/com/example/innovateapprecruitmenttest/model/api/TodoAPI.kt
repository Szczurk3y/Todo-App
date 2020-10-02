package com.example.innovateapprecruitmenttest.model.api

import com.example.innovateapprecruitmenttest.model.response.BaseTodoResponse
import com.example.innovateapprecruitmenttest.model.response.TodoListResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface TodoAPI {
    @GET(".") // BASE_URL == GET TODOS API so let's keep it blank
    @Headers("Content-Type: application/json")
    suspend fun getAllTodos(@Header("Authorization") token: String): TodoListResponse

    @POST(".")
    suspend fun insertTodo(@Header("Authorization") token: String, @Query("title") title: String): Response<BaseTodoResponse>

    @DELETE("{id}")
    suspend fun deleteTodo(@Header("Authorization") token: String, @Path("id") id: String): Response<BaseTodoResponse>

    @PATCH("{id}")
    suspend fun updateTodo(@Header("Authorization") token: String, @Path("id") id: String, @QueryMap options: Map<String, Any>): Response<BaseTodoResponse>

    // Now RxKotlin comes to action! :] (Kotlin coroutines doesn't support Observable return type)
    @GET(".")
    fun orderTodosBy(@Header("Authorization") token: String, @QueryMap options: Map<String, String>): Observable<Response<TodoListResponse>>
}