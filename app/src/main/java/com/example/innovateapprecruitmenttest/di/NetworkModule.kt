package com.example.innovateapprecruitmenttest.di

import com.example.innovateapprecruitmenttest.BuildConfig
import com.example.innovateapprecruitmenttest.model.api.TodoAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://todo.penhr.pl/api/todos/"
const val API_KEY = "e8efdb2cb52c5a868cb2f0265a8310225df7"

fun networkModule() = module {
    // Creates logging interceptor
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    // Creates client
    single {
        val client = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) { // Adds interceptor only in debug mode, so app won't slow in production
            client.addInterceptor(get<HttpLoggingInterceptor>())
        }
        client.build()
    }

    // Creates moshi json adapter
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    // Builds retrofit
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }

    // Creates retrofit instance of TodoAPI
    single {
        get<Retrofit>().create(TodoAPI::class.java)
    }
}