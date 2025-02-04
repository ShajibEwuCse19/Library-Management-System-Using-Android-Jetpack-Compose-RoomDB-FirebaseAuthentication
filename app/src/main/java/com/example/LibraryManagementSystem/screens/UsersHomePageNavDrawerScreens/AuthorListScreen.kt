package com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.components.AuthorListContainer
import com.example.LibraryManagementSystem.components.UserTopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler

@Composable
fun AuthorListScreen(mainViewModel: MainViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            UserTopAppBarWithBackButton(textValue = "Author List") {
                LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
            }
            AuthorListContainer(
                todoItemsFlow = mainViewModel.todos
            )

        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthorListScreenPreview() {
    AuthorListScreen()
}