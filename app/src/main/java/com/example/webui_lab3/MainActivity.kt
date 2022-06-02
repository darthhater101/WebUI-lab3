package com.example.webui_lab3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webui_lab3.data.ContactsRepository
import com.example.webui_lab3.data.UsersRepository
import com.example.webui_lab3.data.api.RetrofitService
import com.example.webui_lab3.databinding.ActivityMainBinding
import com.example.webui_lab3.ui.adapter.ContactsAdapter
import com.example.webui_lab3.ui.factory.ContactViewModelFactory
import com.example.webui_lab3.ui.factory.UsersViewModelFactory
import com.example.webui_lab3.ui.modelview.ContactViewModel
import com.example.webui_lab3.ui.modelview.UsersViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ContactViewModel
    lateinit var viewModelUsers: UsersViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = ContactsAdapter()
    var logedUserId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ContactViewModelFactory(ContactsRepository(retrofitService))).get(ContactViewModel::class.java)
        viewModelUsers = ViewModelProvider(this, UsersViewModelFactory(UsersRepository(retrofitService))).get(UsersViewModel::class.java)

        adapter.setDelHandler {
            viewModel.deleteContact(it.toString(), logedUserId)
        }
        adapter.setUpdHandler {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("id", it)
            startActivityForResult(intent, 0)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.contactList.observe(this, Observer {
            if(it != null)
                adapter.setContactList(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.i("MainActivity", "Error: $it")
        })

        binding.addButton.setOnClickListener {
            val intent = Intent(it.context, UpdateActivity::class.java)
            startActivityForResult(intent, 1)
        }

        binding.logoutButton.setOnClickListener {
            viewModelUsers.logout()
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, 2)
        }

        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            if (requestCode === 0) {
                val firstname: String? = data?.getStringExtra("firstname")
                val lastname: String? = data?.getStringExtra("lastname")
                val phone: String? = data?.getStringExtra("phone")
                val id: Int? = data?.getIntExtra("id", -1)
                if(id!= null && firstname != null && lastname != null && phone != null) {
                    viewModel.updateContact(id, firstname, lastname, phone, logedUserId)
                }
            }
            if (requestCode === 1) {
                val firstname: String? = data?.getStringExtra("firstname")
                val lastname: String? = data?.getStringExtra("lastname")
                val phone: String? = data?.getStringExtra("phone")
                if(firstname != null && lastname != null && phone != null) {
                    viewModel.addContact(firstname, lastname, phone, logedUserId)
                }
            }
            if (requestCode === 2) {
                val id: Int? = data?.getIntExtra("id", -1)
                if(id!= null) {
                    logedUserId = id
                    viewModel.getAllContacts(logedUserId.toString())
                }
            }
        }
    }

}