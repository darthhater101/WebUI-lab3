package com.example.webui_lab3.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.webui_lab3.data.ContactsRepository
import com.example.webui_lab3.ui.modelview.ContactViewModel
import java.lang.IllegalArgumentException

class ContactViewModelFactory constructor(private val repository: ContactsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            ContactViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ContactViewModel not found")
        }
    }
}