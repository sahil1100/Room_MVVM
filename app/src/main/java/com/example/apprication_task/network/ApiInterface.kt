package com.example.apprication_task.network

import com.example.apprication_task.models.ResponseModel
import com.example.apprication_task.models.Users
import com.example.apprication_task.models.userRequest
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("users")
    fun getUserList(): Call<ResponseModel?>?

    @GET("users/{userId}")
    fun getUserDetail(@Path("userId") userId: Int): Call<Users?>?
}