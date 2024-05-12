package com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.components.AlertDialogExample
import com.example.LibraryManagementSystem.components.CheckoutItemsContainer
import com.example.LibraryManagementSystem.components.NormalTextComponent
import com.example.LibraryManagementSystem.components.UserTopAppBarWithBackButton
import com.example.LibraryManagementSystem.data.bookRoomDatabase.CheckoutItem
import com.example.LibraryManagementSystem.data.bookRoomDatabase.MainViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler
import com.example.LibraryManagementSystem.screens.EmailPicker

@Composable
fun ReturnBookScreen(mainViewModel: MainViewModel = viewModel()) {
    var checkoutItem by remember {
        mutableStateOf<CheckoutItem?>(null)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            UserTopAppBarWithBackButton(textValue = "Return Book List") {
                LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
            }
            NormalTextComponent(value = "Your email: ${EmailPicker.email}")
            checkoutItem?.let { todo ->
                val context = LocalContext.current
                AlertDialogExample(
                    onDismissRequest = { checkoutItem = null },
                    onConfirmation = {
                        mainViewModel.returnCheckout(todo)
                        checkoutItem = null
                        Toast.makeText(context, "Successfully Return the book", Toast.LENGTH_SHORT).show()
                    },
                    dialogTitle = "Return Book",
                    dialogText = "Are you sure you want to return this book from your collection?",
                    icon = Icons.Default.AddAlert
                )
            }

            CheckoutItemsContainer(
                mainViewModel,
                checkoutItemFlow = mainViewModel.checkoutAllTodos,
                onItemDelete = { todo ->
                    checkoutItem = todo
                }
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