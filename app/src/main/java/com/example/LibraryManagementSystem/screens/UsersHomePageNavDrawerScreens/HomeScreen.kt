package com.example.LibraryManagementSystem.screens.UsersHomePageNavDrawerScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.components.CartButton
import com.example.LibraryManagementSystem.components.LargeTextButton
import com.example.LibraryManagementSystem.data.homePageUsers.HomeViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LargeTextButton(textValue = "   Book List   ") {
                    LibraryManagementAppRouter.navigateTo(Screen.BookListScreen)
                }
                LargeTextButton(textValue = "  Author List  ") {
                    LibraryManagementAppRouter.navigateTo(Screen.AuthorListScreen)
                }
            }

            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .padding(bottom = 250.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically

            ) {
                LargeTextButton(textValue = "Checkoutable") {
                    LibraryManagementAppRouter.navigateTo(Screen.CheckoutBookScreen)
                }
                LargeTextButton(textValue = "  Returnable  ") {
                    LibraryManagementAppRouter.navigateTo(Screen.ReturnBookScreen)
                }
            }

            Row(
                modifier = Modifier.padding(start = 250.dp, bottom = 16.dp)
            ) {
                CartButton(onClick = {
                    homeViewModel.checkoutBook()
                })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}