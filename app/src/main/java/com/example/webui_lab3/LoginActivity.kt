package com.example.webui_lab3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.webui_lab3.data.UsersRepository
import com.example.webui_lab3.data.api.RetrofitService
import com.example.webui_lab3.databinding.LoginBinding
import com.example.webui_lab3.ui.factory.UsersViewModelFactory
import com.example.webui_lab3.ui.modelview.UsersViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    lateinit var viewModelUsers: UsersViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelUsers = ViewModelProvider(this, UsersViewModelFactory(UsersRepository(retrofitService))).get(UsersViewModel::class.java)

        viewModelUsers.logedUser.observe(this, Observer {
            if(it != null) {
                val logedUserId = it.id
                val intent = Intent()
                intent.putExtra("id", logedUserId)
                setResult(RESULT_OK, intent)
                finish()
            }
        })

        binding.loginButton.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                viewModelUsers.login(username, password)
            }
        }

        binding.registerButton.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter username and password", Toast.LENGTH_SHORT).show()
            } else {
                viewModelUsers.register(username, password)
                Toast.makeText(applicationContext, "User was registered", Toast.LENGTH_SHORT).show()
                binding.editTextUsername.text.clear()
                binding.editTextPassword.text.clear()
            }
        }
    }
}