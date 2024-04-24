package com.example.LibraryManagementSystem.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.homePageAdmin.AdminHomeScreenNavDrawer
import com.example.LibraryManagementSystem.data.homePageUsers.HomeScreenNavDrawer
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens.AdminBookListScreen
import com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens.CreateBook
import com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens.DeleteBook
import com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens.EditBook
import com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens.UpdateBook
import com.example.LibraryManagementSystem.screens.AdminLoginScreen
import com.example.LibraryManagementSystem.screens.LogInScreen
import com.example.LibraryManagementSystem.screens.SignUpScreen
import com.example.LibraryManagementSystem.screens.TermsAndConditionScreen
import com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens.AuthorListScreen
import com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens.BookListScreen
import com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens.CheckoutBookScreen
import com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens.HomeScreen
import com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens.ReturnBookScreen

@Composable
fun LibraryManagementApp(mainViewModel: MainViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(
            targetState = LibraryManagementAppRouter.currentScreen,
            label = ""
        ) { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.TermsAndConditionScreen -> {
                    TermsAndConditionScreen()
                }

                is Screen.LogInScreen -> {
                    LogInScreen()
                }

                is Screen.HomeScreen -> {
                    HomeScreen()
                }

                is Screen.HomeScreenNavDrawer -> {
                    HomeScreenNavDrawer()
                }

                is Screen.AdminLoginScreen -> {
                    AdminLoginScreen()
                }

                is Screen.AdminHomeScreenNavDrawer -> {
                    AdminHomeScreenNavDrawer()
                }
                is Screen.CreateBook -> {
                    CreateBook(mainViewModel)
                }
                is Screen.DeleteBook -> {
                    DeleteBook(mainViewModel)
                }
                is Screen.UpdateBook -> {
                    UpdateBook(mainViewModel)
                }
                is Screen.BookListScreen -> {
                    BookListScreen(mainViewModel)
                }
                is Screen.AuthorListScreen -> {
                    AuthorListScreen(mainViewModel)
                }
                is Screen.CheckoutBookScreen -> {
                    CheckoutBookScreen(mainViewModel)
                }
                is Screen.ReturnBookScreen -> {
                    ReturnBookScreen(mainViewModel)
                }
                is Screen.AdminBookListScreen -> {
                    AdminBookListScreen(mainViewModel)
                }
                is Screen.EditBook -> {
                    EditBook(mainViewModel)
                }
            }
        }
    }
}
