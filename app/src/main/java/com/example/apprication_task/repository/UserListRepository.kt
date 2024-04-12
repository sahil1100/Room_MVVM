package com.example.apprication_task.repository

import androidx.lifecycle.MutableLiveData
import com.example.apprication_task.models.ResponseModel
import com.example.apprication_task.models.Users
import com.example.apprication_task.models.userRequest
import com.example.apprication_task.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListRepository {

    fun userListAPiCall(
        userListApiResponse: MutableLiveData<ResponseModel>,
        apiInterface: ApiInterface
    ) {

        apiInterface.getUserList()!!.enqueue(object :
            Callback<ResponseModel?> {

            override fun onResponse(
                call: Call<ResponseModel?>,
                response: Response<ResponseModel?>
            ) {
                try {
                    userListApiResponse.value = response.body()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    userListApiResponse.value = null
                }
            }

            override fun onFailure(call: Call<ResponseModel?>, t: Throwable) {
                userListApiResponse.value = null
            }


        })
    }

    fun userDetailAPiCall(
        userDetailApiResponse: MutableLiveData<Users>,
        apiInterface: ApiInterface,
        userRequest: userRequest
    ) {

        apiInterface.getUserDetail(userRequest.id)!!.enqueue(object :
            Callback<Users?> {

            override fun onResponse(
                call: Call<Users?>,
                response: Response<Users?>
            ) {
                try {
                    userDetailApiResponse.value = response.body()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    userDetailApiResponse.value = null
                }
            }

            override fun onFailure(call: Call<Users?>, t: Throwable) {
                userDetailApiResponse.value = null
            }


        })
    }
}