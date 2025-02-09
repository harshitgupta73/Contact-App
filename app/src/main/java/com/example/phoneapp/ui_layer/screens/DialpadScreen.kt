package com.example.phoneapp.ui_layer.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.phoneapp.data_layer.tables.ContactTable
//import com.example.phoneapp.ui_layer.AppViewModel
import com.example.phoneapp.ui_layer.navigation.Home
import com.example.phoneapp.ui_layer.viewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun DialpadScreen(navController: NavController,viewModel: AppViewModel= hiltViewModel()) {

    val list = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#"
    )

    val phoneNumber = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Dialpad",
                    color = Color.White,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }, actions = {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }, modifier = Modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            ), navigationIcon = {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable() {
                        navController.navigate(Home)
                    })
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val callIntent = Intent(Intent.ACTION_CALL).apply {
                        data = Uri.parse("tel:${phoneNumber.value}")
                    }
                    context.startActivity(callIntent)
                    viewModel.saveToRecentPhone(phoneNumber = phoneNumber.value)
                    navController.navigate(Home)
                },
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                containerColor = Color.White
            ) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextField(
                    value = phoneNumber.value,
                    onValueChange = {
                        phoneNumber.value = it
                    },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,

                    ),
                    readOnly = true,
                    textStyle = TextStyle(
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    ),

                    )
                Icon(
                    imageVector = Icons.Default.Backspace,
                    contentDescription = null,
                    modifier = Modifier.clickable() {
                        if (phoneNumber.value.isNotEmpty()) {
                            phoneNumber.value = phoneNumber.value.dropLast(1)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.Center,

                ) {
                items(list) {
                    button(btn = it, onClick = {
//                        viewModel.onButtonClick(it)
                        phoneNumber.value += it
                    })
                }
            }

        }

    }
}

@Composable
fun button(btn: String, onClick: () -> Unit) {
    Text(
        text = btn,
        fontSize = 35.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(10.dp)
            .clickable(
                onClick = onClick
            )
    )
}