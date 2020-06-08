package com.example.myapplication.net

import com.example.myapplication.data.HomeListResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*


interface WanApi {
    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int
    ): ApiResult<HomeListResponse>
}