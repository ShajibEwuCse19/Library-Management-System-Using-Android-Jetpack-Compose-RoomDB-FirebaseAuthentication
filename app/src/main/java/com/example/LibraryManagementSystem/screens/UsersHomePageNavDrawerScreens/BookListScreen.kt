package com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.R
import com.example.LibraryManagementSystem.components.AdminBookListContainer
import com.example.LibraryManagementSystem.components.BookInfoContent
import com.example.LibraryManagementSystem.components.TopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler
import com.example.LibraryManagementSystem.ui.theme.constants.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(mainViewModel: MainViewModel = viewModel()) {
    var todoItem by remember {
        mutableStateOf<TodoItem?>(null)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBarWithBackButton(textValue = stringResource(R.string.book_list)) {
                LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
            }
            AdminBookListContainer(
                todoItemsFlow = mainViewModel.todos,
                onButtonClicked = { todo ->
                    todoItem = todo
                }
            )
        }
    }
    todoItem?.let { todo ->
        ModalBottomSheet(
            containerColor = PurpleGrey80,
            onDismissRequest = {
                todoItem = null
            }
        ) {
            BookInfoContent(todoItem = todo)
        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookListScreenPreview() {
    BookListScreen()
}