package com.hks.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.hks.roomdatabase.data.UserDao
import com.hks.roomdatabase.model.User

//UserRepository 는 multiple data sources 에 접근 코드 분리에 추천
class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}