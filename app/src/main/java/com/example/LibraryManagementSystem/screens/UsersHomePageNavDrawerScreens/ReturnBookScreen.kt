package com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.components.HeadingTextComponent
import com.example.LibraryManagementSystem.components.ReturnBookInputBar
import com.example.LibraryManagementSystem.components.UserTopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler

@Composable
fun ReturnBookScreen(mainViewModel: MainViewModel = viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            UserTopAppBarWithBackButton(textValue = "Return Book ") {
                LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
            }
            Spacer(modifier = Modifier.padding(top = 20.dp))
            HeadingTextComponent(value = "Enter Book Information")
            ReturnBookInputBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onAddButtonClick = mainViewModel::addTodo
            )
        }
    }
    SystemBackButtonHandler {
        LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReturnBookScreenPreview() {
    ReturnBookScreen()
}