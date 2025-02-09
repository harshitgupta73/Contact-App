package com.example.phoneapp.ui_layer.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dialpad
//import androidx.compose.material.icons.state.value.value.Dialpad
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.phoneapp.ui_layer.contactState.PhoneState
import com.example.phoneapp.ui_layer.navigation.AddEdit
import com.example.phoneapp.ui_layer.navigation.Dialpad
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
//@Preview(showBackground = true)
fun HomeScreen(navController: NavController) {

    val list = listOf(
        tabItems("Recent", Icons.Default.AccessTime),
        tabItems("Contacts", Icons.Default.Person),
        tabItems("Favourites", Icons.Default.Star),
    )

    val pagerState = rememberPagerState(pageCount = { list.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Contacts", color = Color.White)
                },
                actions = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Dialpad)
                },
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                containerColor = Color.White
            ) {
                Icon(
                    Icons.Default.Dialpad,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }

        },
        floatingActionButtonPosition = FabPosition.Center

    ) {
        Box(
            modifier=Modifier.fillMaxSize().padding(it)
        ){

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Black,
                indicator = {
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage]),
                        color = Color.White,
                        height = 3.dp
                    )
                }
            ) {
                list.fastForEachIndexed { index, pair ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch() {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = pair.icon,
                                contentDescription = pair.title,
                                tint = if (pagerState.currentPage == index) Color.White else Color.Gray
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                pagerState,
                modifier = Modifier.fillMaxSize()
            ) {
                when(it){
                    0 -> RecentContactScreen(navController=navController)
                    1 -> ContactScreen(navController = navController)
                    2 -> FavouriteContactScreen()
                }
            }


        }

    }
}

data class tabItems(
    val title: String,
    val icon: ImageVector
)