package com.example.LibraryManagementSystem.data.homePageUsers

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens.HomeScreen
import com.example.LibraryManagementSystem.ui.theme.constants.WhiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenNavDrawer(homeViewModel: HomeViewModel = viewModel()) {
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
                        .background(Color.Blue)
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 40.dp),
                        text = "My Bookstore",
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider()

                //Homepage
                NavigationDrawerItem(
                    label = { Text(text = "Home", color = Color.Blue) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color.Blue
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        //router
                        navigationController.navigate(HomePageNavScreens.HomeScreen.screen) {
                            popUpTo(0)
                        }
                    }
                )
                //checkout Book
                NavigationDrawerItem(
                    label = { Text(text = "Checkout Book", color = Color.Blue) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Checkout Book",
                            tint = Color.Blue
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        //router
                        /*navigationController.navigate(HomePageNavScreens.CheckoutBookScreen.screen) {
                            popUpTo(0)
                        }*/
                        homeViewModel.checkoutBook()
                    }
                )
                //return book
                NavigationDrawerItem(
                    label = { Text(text = "Return Book", color = Color.Blue) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Return Book",
                            tint = Color.Blue
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        //router
                        /*navigationController.navigate(HomePageNavScreens.ReturnBookScreen.screen) {
                            popUpTo(0)
                        }*/
                        homeViewModel.returnBook()
                    }
                )
                //book list
                NavigationDrawerItem(
                    label = { Text(text = "Book List", color = Color.Blue) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Book List",
                            tint = Color.Blue
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        //router
                        /*navigationController.navigate(HomePageNavScreens.BookListScreen.screen) {
                            popUpTo(0)
                        }*/
                        homeViewModel.bookList()
                    }
                )
                //Author list
                NavigationDrawerItem(
                    label = { Text(text = "Author List", color = Color.Blue) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "Author List",
                            tint = Color.Blue
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        //router
                        /*navigationController.navigate(HomePageNavScreens.AuthorListScreen.screen) {
                            popUpTo(0)
                        }*/
                        homeViewModel.authorList()
                    }
                )
                //logout
                NavigationDrawerItem(
                    label = { Text(text = "Logout", color = Color.Blue) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Blue
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
                        containerColor = Color.Blue,
                        titleContentColor = Color.White,
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
                startDestination = HomePageNavScreens.HomeScreen.screen //main screen "Home"
            ) {
                composable(HomePageNavScreens.HomeScreen.screen) { HomeScreen() }
                /*composable(HomePageNavScreens.CheckoutBookScreen.screen) { CheckoutBookScreen() }
                composable(HomePageNavScreens.ReturnBookScreen.screen) { ReturnBookScreen() }
                composable(HomePageNavScreens.BookListScreen.screen) { BookListScreen() }
                composable(HomePageNavScreens.AuthorListScreen.screen) { AuthorListScreen() }
                composable(HomePageNavScreens.ProfilePageScreen.screen) { ProfilePageScreen() }
                composable(HomePageNavScreens.SettingScreen.screen) { SettingScreen() }*/
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenNavDrawer()
}