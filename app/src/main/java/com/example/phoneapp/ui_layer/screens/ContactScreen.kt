package com.example.phoneapp.ui_layer.screens

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.phoneapp.ui_layer.navigation.AddEdit
import com.example.phoneapp.ui_layer.navigation.Home
import com.example.phoneapp.ui_layer.resources.ProfileImageInitials
import com.example.phoneapp.ui_layer.viewModel.AppViewModel

@Composable
fun ContactScreen(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {

    val state = viewModel.contactState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    navController.navigate(AddEdit)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color(0xFF8fb8db)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("Add Contact", fontSize = 20.sp, color = Color(0xFF8fb8db))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            userScrollEnabled = true
        ) {


            items(state.value.contactList) {

                var showContent by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            showContent = !showContent
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    shape = CardDefaults.elevatedShape,


                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (it.image == null) {
                            ProfileImageInitials(name = it.name)
                        } else {
                            Image(
                                bitmap = BitmapFactory.decodeByteArray(
                                    it.image,
                                    0,
                                    it.image.size
                                ).asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                        }


                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = it.name,
                                fontSize = 25.sp,
                                maxLines = 1,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = it.phoneNumber,
                                fontSize = 20.sp,
                                maxLines = 1,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            Icons.Default.Call,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .clickable {
                                    val callIntent = Intent(Intent.ACTION_CALL).apply {
                                        data = Uri.parse("tel:${it.phoneNumber}")
                                    }
                                    context.startActivity(callIntent)
                                    viewModel.saveToRecent(it)
                                    navController.navigate(Home)
                                }
                                .align(Alignment.CenterVertically)
                                .size(27.dp)
                        )
                    }
                }


                AnimatedVisibility(showContent) {
                    Divider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 5.dp))


                    Row(
                        Modifier.fillMaxWidth().padding(top=5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column (
                            Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(AddEdit)
                                        state.value.name.value = it.name
                                        state.value.id.value = it.id
                                        state.value.phonenumber.value = it.phoneNumber
                                        state.value.email.value = it.email
                                    }
                                    .align(Alignment.CenterHorizontally)
                                    .size(27.dp)
                            )
                            Text("Edit")
                        }
                        Column {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.upsertFavContact(it)
                                    }
                                    .size(27.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text("Favourites")
                        }
                        Column {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.deleteContact(it)
                                    }
                                    .size(27.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}

