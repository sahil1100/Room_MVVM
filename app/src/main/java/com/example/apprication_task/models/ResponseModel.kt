package com.example.apprication_task.models

import com.google.gson.annotations.SerializedName

data class ResponseModel(

    @SerializedName("users") var users: ArrayList<Users> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("skip") var skip: Int? = null,
    @SerializedName("limit") var limit: Int? = null

)