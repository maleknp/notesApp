package com.example.dbproject.ui

import android.icu.text.CaseMap.Title
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dbproject.model.Contact
import com.example.dbproject.model.ContactsDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val db: ContactsDatabase
): ViewModel() {

    fun addContact(note: Contact){
        viewModelScope.launch {
//        val contact = Contact(name = title, phone = desc)
            db.dao.addContact(note)
        }
    }

    fun editContact(note: Contact){
        viewModelScope.launch {
//        val contact = Contact(name = title, phone = desc)
            db.dao.editContact(note)
        }
    }


    fun deleteContact(note: Contact){
        viewModelScope.launch {
            db.dao.deleteContact(note)
        }
        }
    }
