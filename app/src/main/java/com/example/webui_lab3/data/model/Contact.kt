package com.example.webui_lab3.data.model

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("id") val id: Int,
    @SerializedName("firstname") var firstname: String,
    @SerializedName("lastname") var lastname: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("userId") var userId: Int
)

