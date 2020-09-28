package com.example.innovateapprecruitmenttest.model.api

import com.example.innovateapprecruitmenttest.model.response.InsertTodoResponse
import com.example.innovateapprecruitmenttest.model.response.TodoResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface TodoAPI {
    @GET(".") // BASE_URL == GET TODOS API so let's keep it blank
    @Headers("Content-Type: application/json")
    suspend fun getAllTodos(@Header("Authorization") token: String): TodoResponse

    @POST(".")
    suspend fun insertTodo(@Header("Authorization") token: String, @Query("title") title: String): InsertTodoResponse // since post request returns only id, let's keep it simply

    @DELETE("/{id}")
    fun deleteTodo(@Header("Authorization") token: String, @Path("id") id: String): Observable<Response<String>> // since delete request returns only id, we only need to check if request was either successful or not

    @PATCH("/{id}")
    fun updateTodo(@Header("Authorization") token: String, @Path("id") id: String, @QueryMap options: Map<String, String>): Observable<Response<String>>
}