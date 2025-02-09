package com.example.phoneapp.data_layer.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.phoneapp.data_layer.dao.ContactDao
import com.example.phoneapp.data_layer.dao.FavouriteDao
import com.example.phoneapp.data_layer.dao.RecentDao
import com.example.phoneapp.data_layer.tables.ContactTable
import com.example.phoneapp.data_layer.tables.FavouriteTable
import com.example.phoneapp.data_layer.tables.RecentTable

@Database(entities = [ContactTable::class, FavouriteTable::class, RecentTable::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun recentDao():RecentDao
    abstract fun favouriteDao(): FavouriteDao
}