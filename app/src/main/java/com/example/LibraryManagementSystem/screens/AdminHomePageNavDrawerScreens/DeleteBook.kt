package com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.components.AlertDialogExample
import com.example.LibraryManagementSystem.components.TodoItemsContainer
import com.example.LibraryManagementSystem.components.TopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler

@Composable
fun DeleteBook(mainViewModel: MainViewModel = viewModel()) {
    var todoItem by remember {
        mutableStateOf<TodoItem?>(null)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopAppBarWithBackButton(textValue = "Delete Book ") {
                LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreen)
            }

            todoItem?.let { todo ->
                val context = LocalContext.current
                AlertDialogExample(
                    onDismissRequest = { todoItem = null },
                    onConfirmation = {
                        mainViewModel.removeTodo(todo)
                        todoItem = null
                        Toast.makeText(context, "Successfully deleted the book", Toast.LENGTH_SHORT).show()
                    },
                    dialogTitle = "Delete Book",
                    dialogText = "Are you sure you want to delete this book from your collection?",
                    icon = Icons.Default.AddAlert
                )
            }

            TodoItemsContainer(
                todoItemsFlow = mainViewModel.todos,
                onItemDelete = { todo ->
                    todoItem = todo
                }
            )
        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreen)
    }
}