package com.example.webui_lab3.data

import com.example.webui_lab3.data.api.RetrofitService
import com.example.webui_lab3.data.model.LogedUser

class UsersRepository constructor(private val retrofitService: RetrofitService) {
    fun login(user: LogedUser) = retrofitService.login(user)

    fun register(user: LogedUser) = retrofitService.register(user)
}