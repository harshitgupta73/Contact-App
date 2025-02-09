package com.example.phoneapp.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.phoneapp.ui_layer.screens.AddEditContactScreen
import com.example.phoneapp.ui_layer.screens.DialpadScreen
import com.example.phoneapp.ui_layer.screens.HomeScreen
import com.example.phoneapp.ui_layer.viewModel.AppViewModel

@Composable
fun AppNavigation() {
    val navController= rememberNavController()

    NavHost(
        navController=navController,
        startDestination = Home
    ) {
        composable<Home>{
            HomeScreen(navController)
        }
        composable<AddEdit>{
            AddEditContactScreen(navController )
        }
        composable<Dialpad>{
            DialpadScreen(navController)
        }


    }
}