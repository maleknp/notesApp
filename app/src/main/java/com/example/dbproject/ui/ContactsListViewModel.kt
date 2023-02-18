package com.example.dbproject.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbproject.model.Contact
import com.example.dbproject.model.ContactsDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ContactsListViewModel @Inject constructor(
    private val db: ContactsDatabase
): ViewModel() {

    var contacts by mutableStateOf(emptyList<Contact>())
    private set

    init {
        getContacts()
    }

    fun getContacts(){
        db.dao.getContacts().onEach { contactList ->
            contacts = contactList
        }.launchIn(viewModelScope)
    }

}