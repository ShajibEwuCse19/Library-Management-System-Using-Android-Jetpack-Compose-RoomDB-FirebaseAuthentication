package com.example.LibraryManagementSystem.data.homePageAdmin

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.LibraryManagementSystem.data.homePageUsers.HomeViewModel
import com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens.AdminHomeScreen
import com.example.LibraryManagementSystem.ui.theme.constants.WhiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminHomeScreenNavDrawer(homeViewModel: HomeViewModel = viewModel()) {
    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope() //open close
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                //Header
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 40.dp),
                        text = "ADMINISTRATOR",
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Start,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider()

                //Homepage
                NavigationDrawerItem(
                    label = { Text(text = "Home", color = MaterialTheme.colorScheme.primary) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        //router
                        navigationController.navigate(AdminHomePageNavScreens.AdminHomeScreen.screen) {
                            popUpTo(0)
                        }
                    }
                )
                //create book
                NavigationDrawerItem(
                    label = { Text(text = "Create Book", color = MaterialTheme.colorScheme.primary) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        homeViewModel.AdminAddBook()
                    }
                )
                //update book
                NavigationDrawerItem(
                    label = { Text(text = "Update Book", color = MaterialTheme.colorScheme.primary) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Home",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        homeViewModel.AdminUpdateBook()
                        //homeViewModel.AdminAddBook()
                    }
                )
                //Delete book
                NavigationDrawerItem(
                    label = { Text(text = "Delete Book", color = MaterialTheme.colorScheme.primary) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        homeViewModel.AdminDeleteBook()
                        //homeViewModel.AdminAddBook()
                    }
                )
                // book list
                NavigationDrawerItem(
                    label = { Text(text = "Book List", color = MaterialTheme.colorScheme.primary) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = "delete",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        homeViewModel.adminBookList()
                        //homeViewModel.AdminAddBook()
                    }
                )
                //logout
                NavigationDrawerItem(
                    label = { Text(text = "Logout", color = MaterialTheme.colorScheme.primary) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        //change screen based on button clicked
                        homeViewModel.logout()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                val coroutineScope = rememberCoroutineScope()
                TopAppBar(
                    title = { Text(text = "Library Management System") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = WhiteColor
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Rounded.Menu, contentDescription = "Menu Button"
                            )
                        }
                    }
                )
            }
        ) {
            NavHost(
                navController = navigationController,
                startDestination = AdminHomePageNavScreens.AdminHomeScreen.screen //main screen "Home"
            ) {
                composable(AdminHomePageNavScreens.AdminHomeScreen.screen) { AdminHomeScreen() }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminHomeScreenNavDrawerPreview() {
    AdminHomeScreenNavDrawer()
}