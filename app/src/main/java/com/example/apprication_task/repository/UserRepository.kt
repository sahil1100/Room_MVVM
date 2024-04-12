package com.example.apprication_task.repository

import com.example.apprication_task.dao.UserDao
import com.example.apprication_task.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun updateUser(
        userId: Int,
        firstname: String,
        lastname: String,
        email: String,
        number: String,
        image: String
    ) {
        withContext(Dispatchers.IO) {
            val user = userDao.getUserBYId(userId.toLong())
            user.firstname = firstname
            user.lastname = lastname
            user.email = email
            user.number = number
            user.image = image
            userDao.updateUser(user)
        }
    }

    suspend fun getUserById(userId: Int): UserEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUserBYId(userId.toLong())
        }
    }
}
