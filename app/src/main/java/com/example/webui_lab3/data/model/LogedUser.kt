package com.example.webui_lab3.data.model

import com.google.gson.annotations.SerializedName

data class LogedUser(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
)
