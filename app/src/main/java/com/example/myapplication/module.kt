package com.example.myapplication

import com.example.myapplication.ui.home.viewmodel.HomeViewModel
import com.example.myapplication.ui.home.repository.HomeRepository
import com.example.myapplication.ui.home.repository.HomeRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<HomeRepository> { HomeRepositoryImpl() }
    factory {
        HomeRepositoryImpl()
    }
    viewModel {
        HomeViewModel(get())
    }
}