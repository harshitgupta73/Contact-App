package com.example.phoneapp.data_layer.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favourite_table")
data class FavouriteTable(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val dob: String,
    val image: ByteArray?
)
