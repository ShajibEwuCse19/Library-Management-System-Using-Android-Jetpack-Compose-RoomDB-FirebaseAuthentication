package com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.components.CheckoutBookInfoInputWithDate
import com.example.LibraryManagementSystem.components.CheckoutBookListContainer
import com.example.LibraryManagementSystem.components.TopAppBarWithBackAndRefresh
import com.example.LibraryManagementSystem.components.TopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.data.bookRoomDatabase.TodoItem
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler
import com.example.LibraryManagementSystem.ui.theme.constants.PurpleGrey80

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutBookScreen(mainViewModel: MainViewModel = viewModel()) {
    var todoItem by remember {
        mutableStateOf<TodoItem?>(null)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBarWithBackAndRefresh(
                textValue = "Checkout Book",
                onBackButtonClicked = {
                    LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
                },
                onRefreshButtonClicked = {
                    LibraryManagementAppRouter.navigateTo(Screen.CheckoutBookScreen)
                }
            )
            CheckoutBookListContainer(
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
            CheckoutBookInfoInputWithDate(mainViewModel,todoItem = todo) {
                todoItem = it
            }
            //LibraryManagementAppRouter.navigateTo(Screen.EditBook(todo=todo))
        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckoutBookScreenPreview() {
    CheckoutBookScreen()
}