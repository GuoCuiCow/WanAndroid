package com.example.myapplication.data

data class HomeListResponse<T>(
    val curPage: Int,
    val datas: MutableList<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
data class HomeBean(
    val chapterName :String
)