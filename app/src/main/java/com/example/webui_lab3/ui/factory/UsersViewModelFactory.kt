package com.example.webui_lab3.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.webui_lab3.data.UsersRepository
import com.example.webui_lab3.ui.modelview.UsersViewModel
import java.lang.IllegalArgumentException

class UsersViewModelFactory constructor(private val repository: UsersRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            UsersViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("UsersViewModel not found")
        }
    }
}