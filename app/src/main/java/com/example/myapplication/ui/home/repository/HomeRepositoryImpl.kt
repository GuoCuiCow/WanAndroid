package com.example.myapplication.ui.home.repository

import com.example.myapplication.api

class HomeRepositoryImpl : HomeRepository {

    override suspend fun getArticles(page: Int) = api.getArticleList(page)
}