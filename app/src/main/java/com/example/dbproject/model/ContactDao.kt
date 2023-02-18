package com.example.dbproject.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert
    suspend fun addContact(contact: Contact)

    @Update
    suspend fun editContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("select * from contact")
    fun getContacts(): Flow<List<Contact>>

}