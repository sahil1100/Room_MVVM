package com.example.apprication_task.models

import com.google.gson.annotations.SerializedName

data class UserData (
    @SerializedName("id") var id: Int? = null,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("image") var image: String? = null,
)