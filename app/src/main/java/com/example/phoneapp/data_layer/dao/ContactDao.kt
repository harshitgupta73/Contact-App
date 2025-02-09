package com.example.phoneapp.data_layer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.phoneapp.data_layer.tables.ContactTable
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun upsertContact(contact: ContactTable)

    @Delete
    suspend fun deleteContact(contact: ContactTable)

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getAllContacts(): Flow<List<ContactTable>>

}