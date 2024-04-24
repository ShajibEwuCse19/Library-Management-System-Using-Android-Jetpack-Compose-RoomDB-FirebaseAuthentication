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
import androidx.compose.material.icons.outlined.Person
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
import com.example.LibraryManagementSystem.components.CheckBoxComponent
import com.example.LibraryManagementSystem.components.CircularProgressTimer
import com.example.LibraryManagementSystem.components.ClickableLoginTextComponent
import com.example.LibraryManagementSystem.components.DividerTextComponent
import com.example.LibraryManagementSystem.components.HeadingTextComponent
import com.example.LibraryManagementSystem.components.MyTextField
import com.example.LibraryManagementSystem.components.NormalTextComponent
import com.example.LibraryManagementSystem.components.PasswordTextFieldComponent
import com.example.LibraryManagementSystem.data.signup.SignupUiEvent
import com.example.LibraryManagementSystem.data.signup.SignupViewModel
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.example.LibraryManagementSystem.navigation.SystemBackButtonHandler


@Composable
fun SignUpScreen(signupViewModel: SignupViewModel = viewModel()) {
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
                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                MyTextField(
                    labelValue = stringResource(id = R.string.first_name),
                    Icons.Outlined.Person,
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUiEvent.FirstNameChanged(it))
                    },
                    signupViewModel.signupUiState.value.firstNameError
                )
                MyTextField(
                    labelValue = stringResource(id = R.string.last_name),
                    Icons.Outlined.Person,
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUiEvent.LastNameChanged(it))
                    },
                    signupViewModel.signupUiState.value.lastNameError
                )
                MyTextField(
                    labelValue = stringResource(id = R.string.email),
                    Icons.Outlined.Email,
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUiEvent.EmailChanged(it))
                    },
                    signupViewModel.signupUiState.value.emailError
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    Icons.Outlined.Lock,
                    onTextSelected = {
                        signupViewModel.onEvent(SignupUiEvent.PasswordChanged(it))
                    },
                    signupViewModel.signupUiState.value.passwordError
                )
                Spacer(modifier = Modifier.height(20.dp))
                CheckBoxComponent(
                    value = stringResource(R.string.terms_and_condition_full_text),
                    onTextSelected = {
                        LibraryManagementAppRouter.navigateTo(Screen.TermsAndConditionScreen)
                    },
                    onCheckedChange = {
                        //taking result from UI Component and passing the status to UI State from here (Accepted or Error)
                        signupViewModel.onEvent(SignupUiEvent.PrivacyPolicyCheckboxClicked(it))
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        signupViewModel.onEvent(SignupUiEvent.RegistrationButtonClicked)
                    },
                    isEnabled = signupViewModel.allValidationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))
                ClickableLoginTextComponent(
                    onTextSelected = {
                        LibraryManagementAppRouter.navigateTo(Screen.LogInScreen)
                    }
                )
            }
        }
        SystemBackButtonHandler {
            LibraryManagementAppRouter.navigateTo(Screen.LogInScreen)
        }

        if(signupViewModel.signUpInProgress.value) CircularProgressTimer()
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}