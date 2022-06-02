package com.example.webui_lab3.data.api

import com.example.webui_lab3.data.model.Contact
import com.example.webui_lab3.data.model.LogedUser
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    @POST("/login")
    fun login(@Body user: LogedUser): Call<LogedUser>

    @POST("/users")
    fun register(@Body user: LogedUser): Call<LogedUser>

    @GET("/users/{id}/contacts")
    fun getAllContacts(@Path("id") userId: String): Call<List<Contact>>

    @DELETE("/contact/{id}")
    fun deleteContact(@Path("id") contactId: String): Call<List<Contact>>

    @POST("/contact")
    fun addContact(@Body contact: Contact): Call<Contact>

    @POST("/contact/{id}")
    fun updateContact(@Path("id") contactId: String, @Body contact: Contact): Call<Contact>

    companion object {

        const val BASE_URL = "http://192.168.1.40:3000"

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}