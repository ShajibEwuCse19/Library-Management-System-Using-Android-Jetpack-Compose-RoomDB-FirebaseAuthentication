package com.example.LibraryManagementSystem.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.LibraryManagementSystem.R
import com.example.LibraryManagementSystem.components.ButtonComponent
import com.example.LibraryManagementSystem.components.CircularProgressTimer
import com.example.LibraryManagementSystem.components.ClickableAdminTextComponent
import com.example.LibraryManagementSystem.components.ClickableRegisterTextComponent
import com.example.LibraryManagementSystem.components.DividerTextComponent
import com.example.LibraryManagementSystem.components.HeadingTextComponent
import com.example.LibraryManagementSystem.components.MyTextField
import com.example.LibraryManagementSystem.components.NormalTextComponent
import com.example.LibraryManagementSystem.components.PasswordTextFieldComponent
import com.example.LibraryManagementSystem.components.UnderLinedTextComponent
import com.example.LibraryManagementSystem.data.login.LoginUiEvent
import com.example.LibraryManagementSystem.data.login.LoginViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen


@Composable
fun LogInScreen(loginViewModel: LoginViewModel = viewModel()) {
    var maxSize = Modifier.fillMaxSize()
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.Hello))
                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(20.dp))
                MyTextField(
                    labelValue = stringResource(id = R.string.email),
                    Icons.Outlined.Email,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUiEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUiState.value.emailError
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    Icons.Outlined.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUiEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUiState.value.passwordError
                )
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUiEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                UnderLinedTextComponent(value = stringResource(R.string.forgot_password))
                Spacer(modifier = Modifier.height(20.dp))
                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))
                ClickableRegisterTextComponent(
                    onTextSelected = {
                        LibraryManagementAppRouter.navigateTo(Screen.SignUpScreen)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ClickableAdminTextComponent(
                    onTextSelected = {
                        LibraryManagementAppRouter.navigateTo(Screen.AdminLoginScreen)
                    }
                )
            }
        }
        if (loginViewModel.loginInProgress.value) CircularProgressTimer()
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreviewOfLoginUpScreen() {
    LogInScreen()
}