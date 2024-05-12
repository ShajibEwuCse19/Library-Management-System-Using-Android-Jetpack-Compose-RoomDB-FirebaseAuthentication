package com.example.LibraryManagementSystem.screens.AdminHomePageNavDrawerScreens

import android.content.ContentResolver
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.LibraryManagementSystem.components.HeadingTextComponent
import com.example.LibraryManagementSystem.components.TodoInputBar
import com.example.LibraryManagementSystem.components.TopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateBook(mainViewModel: MainViewModel) {
    val current = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            TopAppBarWithBackButton(textValue = "Add Book") {
                LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreen)
            }
            HeadingTextComponent(value = "Enter Book Information")
            TodoInputBar(
                mainViewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onAddButtonClick = { title, author, publisher, isbn, price, currency, date, noOfPage, imageUri ->
                     mainViewModel.addTodo(title, author, publisher, isbn, price, currency, date, noOfPage, imageUri, current.contentResolver)
                },
            )
        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreen)
    }
}

