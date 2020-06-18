package com.example.myapplication

import com.example.myapplication.database.AppDataBase
import com.example.myapplication.net.ApiFactory


val api = ApiFactory.wanApi
val db = AppDataBase.getDatabase(App.appContext)

