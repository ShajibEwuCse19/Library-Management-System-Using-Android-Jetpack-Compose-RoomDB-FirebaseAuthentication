package com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.components.TodoItemsContainer
import com.example.LibraryManagementSystem.components.UserTopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler

@Composable
fun DeleteBook(mainViewModel: MainViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            UserTopAppBarWithBackButton(textValue = "Delete Book ") {
                LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreenNavDrawer)
            }
            TodoItemsContainer(
                todoItemsFlow = mainViewModel.todos,
                onItemClick = mainViewModel::toggleTodo,
                onItemDelete = mainViewModel::removeTodo,
            )

        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreenNavDrawer)
    }
}