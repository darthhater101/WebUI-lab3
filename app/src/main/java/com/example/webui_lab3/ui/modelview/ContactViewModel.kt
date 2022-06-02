package com.example.webui_lab3.ui.modelview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.webui_lab3.data.ContactsRepository
import com.example.webui_lab3.data.model.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactViewModel constructor(private val repository: ContactsRepository) : ViewModel() {

    val contactList = MutableLiveData<List<Contact>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllContacts(userId: String) {
        val response = repository.getAllContacts(userId)
        response.enqueue(object: Callback<List<Contact>> {
            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                contactList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Contact>>?, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun deleteContact(contactId: String, userId: Int) {
        val response = repository.deleteContact(contactId)
        response.enqueue(object: Callback<List<Contact>> {
            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                contactList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Contact>>?, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun addContact(firstname: String, lastname: String, phone: String, userId: Int) {
        val contact = Contact(-1, firstname, lastname, phone, userId)
        val response = repository.addContact(contact)
        response.enqueue(object: Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                val list = contactList.value?.toMutableList()
                if (list != null) {
                    val contact = response.body()
                    if (contact != null) {
                        list.add(contact)
                    }
                }
                contactList.postValue(list)
            }

            override fun onFailure(call: Call<Contact>?, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun updateContact(id: Int, firstname: String, lastname: String, phone: String, userId: Int) {
        val contact = Contact(id, firstname, lastname, phone, userId)
        val response = repository.updateContact(id.toString(), contact)
        response.enqueue(object: Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                val list = contactList.value?.toMutableList()
                if (list != null) {
                    val contact = response.body()
                    if (contact != null) {
                        for(item in list) {
                            if(item.id == contact.id) {
                                item.firstname = contact.firstname
                                item.lastname = contact.lastname
                                item.phone = contact.phone
                                item.userId = contact.userId
                            }
                        }
                    }
                }
                contactList.postValue(list)
            }

            override fun onFailure(call: Call<Contact>?, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}