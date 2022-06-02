package com.example.webui_lab3.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.webui_lab3.data.model.Contact
import com.example.webui_lab3.databinding.ItemContactBinding

class ContactsAdapter () : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    var contacts = mutableListOf<Contact>()

    var deleteHandler: (Int) -> Unit = {}
    var updateHandler: (Int) -> Unit = {}

    @SuppressLint("NotifyDataSetChanged")
    fun setContactList(contacts: List<Contact>) {
        this.contacts = contacts.toMutableList()
        notifyDataSetChanged()
    }

    fun setDelHandler(handler: (Int) -> Unit) {
        deleteHandler = handler
    }

    fun setUpdHandler(handler: (Int) -> Unit) {
        updateHandler = handler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.contactName.text = contact.firstname + " " + contact.lastname
        holder.binding.phone.text = contact.phone
        holder.binding.updateButton.text = "Update"
        holder.binding.updateButton.setOnClickListener {
            updateHandler(contacts[position].id)
        }
        holder.binding.deleteButton.text = "Delete"
        holder.binding.deleteButton.setOnClickListener {
            deleteHandler(contacts[position].id)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    inner class ContactViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}