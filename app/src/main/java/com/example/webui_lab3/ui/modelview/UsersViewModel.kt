package com.example.webui_lab3.ui.modelview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.webui_lab3.data.UsersRepository
import com.example.webui_lab3.data.model.LogedUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel constructor(private val repository: UsersRepository) : ViewModel() {
    val logedUser = MutableLiveData<LogedUser>()
    val errorMessage = MutableLiveData<String>()

    fun login(username: String, password: String) {
        val user = LogedUser(-1, username, password)

        val response = repository.login(user)

        response.enqueue(object: Callback<LogedUser> {
            override fun onResponse(call: Call<LogedUser>, response: Response<LogedUser>) {
                Log.i("LogIn", response.body().toString())
                logedUser.postValue(response.body())
            }

            override fun onFailure(call: Call<LogedUser>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun register(username: String, password: String) {
        val user = LogedUser(-1, username, password)
        val response = repository.register(user)

        response.enqueue(object: Callback<LogedUser> {
            override fun onResponse(call: Call<LogedUser>, response: Response<LogedUser>) {

            }

            override fun onFailure(call: Call<LogedUser>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun logout() {
        logedUser.postValue(null)
    }

}