package com.example.phoneapp.data_layer.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.phoneapp.data_layer.tables.RecentTable
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateRecentContact(recent: RecentTable)

    @Query("SELECT * FROM recent_tables ORDER BY lastAccessed DESC LIMIT 30")
    fun getLastAccessedContact(): Flow<List<RecentTable>>

}