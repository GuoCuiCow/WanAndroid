package com.example.myapplication.ui.home.viewmodel

import androidx.lifecycle.*
import androidx.room.Room
import com.example.myapplication.App
import com.example.myapplication.data.HomeBean
import com.example.myapplication.data.HomeListResponse
import com.example.myapplication.data.User
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.db
import com.example.myapplication.net.ApiResult
import com.example.myapplication.ui.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class HomeViewModel(private val repository: HomeRepository) : RequestViewModel() {
    private val pageLive = MutableLiveData<Int>()
    var pageNum = 0
    fun loadMore() {
        pageNum++
        pageLive.value = pageNum
    }

    val list = pageLive.switchMap {
        liveData {
            emit(repository.getArticles(it))
        }
    }


}