package com.example.webui_lab3.data

import com.example.webui_lab3.data.api.RetrofitService
import com.example.webui_lab3.data.model.Contact
import retrofit2.http.Body

class ContactsRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllContacts(userId: String) = retrofitService.getAllContacts(userId)

    fun deleteContact(contactId: String) = retrofitService.deleteContact(contactId)

    fun addContact(contact: Contact) = retrofitService.addContact(contact)

    fun updateContact(contactId: String, contact: Contact) = retrofitService.updateContact(contactId, contact)

}