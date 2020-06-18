package com.example.myapplication.ui.home.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.dao.UserDao
import com.example.myapplication.data.User

class UserRepository(private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAll()
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}