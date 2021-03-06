package com.example.myapplication.data

data class HomeListResponse(
    val curPage: Int,
    val datas: MutableList<HomeBean>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
data class HomeBean(
    val chapterName :String
)