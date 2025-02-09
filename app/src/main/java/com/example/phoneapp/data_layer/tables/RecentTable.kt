package com.example.phoneapp.data_layer.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
@Entity(tableName = "recent_tables")
data class RecentTable(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    val phoneNumber: String,
    val lastAccessed: Long,
    val image: ByteArray?
)
