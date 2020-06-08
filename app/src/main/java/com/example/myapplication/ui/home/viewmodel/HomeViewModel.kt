package com.example.myapplication.ui.home.viewmodel

import com.example.myapplication.ui.home.repository.HomeRepository


class HomeViewModel(private val repository: HomeRepository) : RequestViewModel() {

    fun getArticleList() = apiLiveData {
        repository.getArticles(0)
    }

}