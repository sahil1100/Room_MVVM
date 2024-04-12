package com.example.apprication_task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apprication_task.entity.UserEntity
import com.example.apprication_task.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserListRoomViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUsersFromDatabase(): Flow<List<UserEntity>> {
        return userRepository.getAllUsers()
    }
}
