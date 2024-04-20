package com.example.LibraryManagementSystem.data.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.LibraryManagementSystem.data.rules.Validator
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUiState = mutableStateOf(LoginUiState())
    var allValidationPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                loginUiState.value = loginUiState.value.copy(email = event.email)
            }

            is LoginUiEvent.PasswordChanged -> {
                loginUiState.value = loginUiState.value.copy(password = event.password)
            }

            is LoginUiEvent.LoginButtonClicked -> {
                login()
            }
        }
        validateLoginUiDataWithRules()
    }

    private fun validateLoginUiDataWithRules() {
        //check rules
        val emailResult = Validator.validateEmail(email = loginUiState.value.email)
        val passwordResult = Validator.validatePassword(password = loginUiState.value.password)

        //global update based on result
        loginUiState.value = loginUiState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        //all validation
        allValidationPassed.value = emailResult.status && passwordResult.status
    }

    private fun login() {
        loginInProgress.value = true
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(loginUiState.value.email, loginUiState.value.password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_login_success")
                Log.d(TAG, "${it.isSuccessful}")

                //admin login
                if (it.isSuccessful && loginUiState.value.email == "admin@gmail.com" && loginUiState.value.password == "123456") {
                    loginInProgress.value = false
                    LibraryManagementAppRouter.navigateTo(Screen.AdminHomeScreenNavDrawer)
                }
                //user login
                else if (it.isSuccessful) {
                    loginInProgress.value = false
                    LibraryManagementAppRouter.navigateTo(Screen.HomeScreenNavDrawer)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Login_Failure")
                Log.d(TAG, "Login_exception = ${it.message}")
                Log.d(TAG, "Login_exception = ${it.localizedMessage}")

                loginInProgress.value = false
            }
    }
}