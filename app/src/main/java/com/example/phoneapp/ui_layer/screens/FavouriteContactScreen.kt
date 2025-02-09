package com.example.phoneapp.ui_layer.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.phoneapp.ui_layer.viewModel.AppViewModel

@Composable
fun FavouriteContactScreen(viewModel: AppViewModel= hiltViewModel()) {
    val state = viewModel.favouriteState.collectAsState()

    Text("Huii")
}