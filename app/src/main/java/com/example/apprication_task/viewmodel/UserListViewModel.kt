package com.example.apprication_task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apprication_task.MyApplication
import com.example.apprication_task.entity.UserEntity
import com.example.apprication_task.models.ResponseModel
import com.example.apprication_task.models.Users
import com.example.apprication_task.models.userRequest
import com.example.apprication_task.network.ApiInterface
import com.example.apprication_task.repository.UserListRepository
import com.example.apprication_task.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserListViewModel : ViewModel() {

    var userListResponse = MutableLiveData<ResponseModel>()
    var userDetailResponse = MutableLiveData<Users>()

    private val userRepository: UserRepository = UserRepository(MyApplication.database.userDao())

    private val _user = MutableLiveData<UserEntity?>()
    val userData: MutableLiveData<UserEntity?>
        get() = _user

    fun updateUser(
        userId: Int,
        firstname: String,
        lastname: String,
        email: String,
        number: String,
        image: String
    ) {
        viewModelScope.launch {
            userRepository.updateUser(userId, firstname, lastname, email, number, image)
        }
    }

    fun callUserListApi(userListRepository: UserListRepository, apiInterface: ApiInterface) {

        userListRepository.userListAPiCall(userListResponse, apiInterface)
    }

    fun callUserDetailApi(userListRepository: UserListRepository, apiInterface: ApiInterface, userRequest: userRequest) {

        userListRepository.userDetailAPiCall(userDetailResponse, apiInterface, userRequest)
    }

    fun getUsersFromDatabase(userRepository: UserRepository): Flow<List<UserEntity>> {
        return userRepository.getAllUsers()
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            val user = userRepository.getUserById(userId)
            _user.value = user
        }
    }
}