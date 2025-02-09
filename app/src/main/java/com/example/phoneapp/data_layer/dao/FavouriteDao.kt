package com.example.phoneapp.data_layer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.phoneapp.data_layer.tables.FavouriteTable
import kotlinx.coroutines.flow.Flow
@Dao
interface FavouriteDao {

    @Upsert
    suspend fun upsertFavourite(favourite: FavouriteTable)

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteTable)

    @Query("SELECT * FROM favourite_table ORDER BY name ASC")
    fun getAllFavourites(): Flow<List<FavouriteTable>>
}