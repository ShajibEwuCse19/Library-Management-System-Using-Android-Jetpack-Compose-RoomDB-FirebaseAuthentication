package com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.components.TodoInputBar
import com.example.LibraryManagementSystem.components.TodoItemsContainer
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler

@Composable
fun CreateBook(mainViewModel: MainViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TodoInputBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onAddButtonClick = mainViewModel::addTodo
            )
            Divider()
            TodoItemsContainer(
                todoItemsFlow = mainViewModel.todos,
                // 3. Method Reference Expression
                onItemClick = mainViewModel::toggleTodo,
                onItemDelete = mainViewModel::removeTodo,
            )

        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreenNavDrawer)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    CreateBook()
}