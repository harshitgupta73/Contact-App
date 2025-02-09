package com.example.phoneapp.data_layer.repo

import com.example.phoneapp.data_layer.appDatabase.AppDatabase
import com.example.phoneapp.data_layer.tables.ContactTable
import com.example.phoneapp.data_layer.tables.FavouriteTable
import com.example.phoneapp.data_layer.tables.RecentTable

class AppRepo(val database: AppDatabase) {
    //Contact
    suspend fun upsertContact(contact: ContactTable) {
        database.contactDao().upsertContact(contact)
    }

    suspend fun deleteContact(contact: ContactTable) {
        database.contactDao().deleteContact(contact)
    }

    fun getAllContacts() = database.contactDao().getAllContacts()

    //Recent
    suspend fun insertRecentContact(recent: RecentTable) {
        database.recentDao().insertOrUpdateRecentContact(recent)
    }

    fun getLastAccessedContact() =
        database.recentDao().getLastAccessedContact()


    //Favourite
    suspend fun insertFavouriteContact(favourite: FavouriteTable) {
        database.favouriteDao().upsertFavourite(favourite)
    }

    suspend fun deleteFavouriteContact(favourite: FavouriteTable) {
        database.favouriteDao().deleteFavourite(favourite)
    }

    fun getAllFavourites() = database.favouriteDao().getAllFavourites()


}