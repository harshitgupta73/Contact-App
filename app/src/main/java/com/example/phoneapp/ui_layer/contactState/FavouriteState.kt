package com.example.phoneapp.ui_layer.contactState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.phoneapp.data_layer.tables.ContactTable
import com.example.phoneapp.data_layer.tables.FavouriteTable

data class FavouriteState(
    val favouriteList: List<FavouriteTable> = emptyList(),
)