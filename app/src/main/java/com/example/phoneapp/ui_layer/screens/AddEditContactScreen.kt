package com.example.phoneapp.ui_layer.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.phoneapp.ui_layer.navigation.Home
import com.example.phoneapp.ui_layer.viewModel.AppViewModel
import com.example.phoneapp.R
import com.example.phoneapp.ui_layer.contactState.PhoneState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContactScreen(navController: NavController, viewModel: AppViewModel = hiltViewModel()) {
    val state = viewModel.contactState.collectAsState()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            if (it != null) {
                val inputStream = context.contentResolver.openInputStream(it)
                val byte = inputStream?.readBytes()
                state.value.image.value = byte
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Add Contact",
                    color = Color.White,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }, actions = {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable() {
                        viewModel.upsertContact()
                        navController.navigate(Home)

                    })
                Spacer(modifier = Modifier.width(15.dp))
                Icon(Icons.Default.Share, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(15.dp))
                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
            }, modifier = Modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            ), navigationIcon = {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable() {
                        navController.navigate(Home)
                    })
            })
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            if (state.value.image.value == null) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Gray),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            launcher.launch("image/*")
                        }
                        .border(2.dp, Color.Gray, CircleShape)
                        .background(Color.LightGray)
                )
            } else {
                Image(
                    bitmap = BitmapFactory.decodeByteArray(
                        state.value.image.value, 0, state.value.image.value!!.size
                    ).asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            launcher.launch("image/*")
                        }
                        .size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Add Picture",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {
                        launcher.launch("image/*")
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = state.value.name.value,
                    onValueChange = {
                        state.value.name.value = it
                    },
                    label = {
                        Text(text = "Name", color = Color.White)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = state.value.phonenumber.value,
                    onValueChange = {
                        state.value.phonenumber.value = it
                    },
                    label = {
                        Text(text = "Number", color = Color.White)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = state.value.email.value,
                    onValueChange = {
                        state.value.email.value = it
                    },
                    label = {
                        Text(text = "Email", color = Color.White)
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = state.value.dob.value,
                    onValueChange = {
                        state.value.dob.value = it
                    },
                    label = {
                        Text(text = "Date Of Birth", color = Color.White)
                    }
                )
            }

        }
    }

}