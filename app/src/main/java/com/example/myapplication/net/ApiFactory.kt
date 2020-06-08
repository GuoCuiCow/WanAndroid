package com.example.myapplication.net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//ApiFactory to create wan android Api
object ApiFactory{

    private val loggingInterceptor =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //OkhttpClient for building http request url
    private val wanClient = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .build()

    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(wanClient)
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val wanApi : WanApi = retrofit().create(WanApi::class.java)

}
