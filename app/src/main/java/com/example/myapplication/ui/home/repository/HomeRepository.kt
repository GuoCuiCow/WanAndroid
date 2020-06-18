package com.example.myapplication.ui.home.repository

import com.example.myapplication.data.HomeBean
import com.example.myapplication.data.HomeListResponse
import com.example.myapplication.net.ApiResult

interface HomeRepository {
    suspend fun getArticles(page: Int): ApiResult<HomeListResponse<HomeBean>>
}