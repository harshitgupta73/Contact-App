package com.example.phoneapp.ui_layer.screens

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.phoneapp.ui_layer.navigation.Home
import com.example.phoneapp.ui_layer.resources.ProfileImageInitials
import com.example.phoneapp.ui_layer.viewModel.AppViewModel

@Composable
fun RecentContactScreen(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {

    val context = LocalContext.current
    val state = viewModel.recentContactList.collectAsState()



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        userScrollEnabled = true
    ) {
        items(state.value) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
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
                            modifier = Modifier.size(40.dp).clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = it.name,
                        fontSize = 25.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(50.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f) // Ensures that the icon is pushed to the end of the Row
                            .fillMaxHeight(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
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

                                    navController.navigate(Home)
                                }
                        )
                    }
                }
            }
        }
    }

}
