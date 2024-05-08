package com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.R
import com.example.LibraryManagementSystem.components.AdminBookListContainer
import com.example.LibraryManagementSystem.components.AlertDialogExample
import com.example.LibraryManagementSystem.components.BookInfoContent
import com.example.LibraryManagementSystem.components.BookUpdateInfoContent
import com.example.LibraryManagementSystem.components.HeadingTextComponent
import com.example.LibraryManagementSystem.components.TopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler
import com.example.LibraryManagementSystem.ui.theme.constants.GreenColor
import com.example.LibraryManagementSystem.ui.theme.constants.Pink80
import com.example.LibraryManagementSystem.ui.theme.constants.PinkColor
import com.example.LibraryManagementSystem.ui.theme.constants.Purple80
import com.example.LibraryManagementSystem.ui.theme.constants.PurpleGrey80
import com.example.LibraryManagementSystem.ui.theme.constants.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminBookListScreen(mainViewModel: MainViewModel = viewModel()) {
    var todoItem by remember {
        mutableStateOf<TodoItem?>(null)
    }
    var deleteTodoItem by remember {
        mutableStateOf<TodoItem?>(null)
    }
    var updateTodoItem by remember {
        mutableStateOf<TodoItem?>(null)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBarWithBackButton(textValue = stringResource(R.string.book_list)) {
                LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreen)
            }
            AdminBookListContainer(
                todoItemsFlow = mainViewModel.todos,
                onButtonClicked = { todo ->
                    todoItem = todo
                },
                onDeleteButtonClicked = { todo ->
                    //mainViewModel.removeTodo(todo)
                    deleteTodoItem = todo //passing the todo received from container
                },
                onEditButtonClicked = {todo ->
                    updateTodoItem = todo
                },
                onDetailsButtonClicked = {todo ->
                    todoItem = todo
                }
            )
        }
    }

    //delete alert dialog
    deleteTodoItem?.let { todo ->
        val context = LocalContext.current
        AlertDialogExample(
            onDismissRequest = { deleteTodoItem = null },
            onConfirmation = {
                mainViewModel.removeTodo(todo)
                deleteTodoItem = null
                Toast.makeText(context, "Successfully deleted the book", Toast.LENGTH_SHORT).show()
            },
            dialogTitle = "Delete Book",
            dialogText = "Are you sure you want to delete this book from your collection?",
            icon = Icons.Default.AddAlert
        )
    }

    //update book bottom sheet
    updateTodoItem?.let { todo ->
        ModalBottomSheet(
            containerColor = PurpleGrey80,
            onDismissRequest = {
                updateTodoItem = null
            }
        ) {
            BookUpdateInfoContent(mainViewModel,todoItem = todo) {
                updateTodoItem = it
            }
        }
    }

    //book details bottom sheet
    todoItem?.let { todo ->
        ModalBottomSheet(
            containerColor = Secondary,
            contentColor = Color.Black,
            onDismissRequest = {
                todoItem = null
            }
        ) {
            BookInfoContent(todoItem = todo)
        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreen)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdminBookListScreenPreview() {
    AdminBookListScreen()
}