package com.example.myapplication.net

data class ApiResult<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)