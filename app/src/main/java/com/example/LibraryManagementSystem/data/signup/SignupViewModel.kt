package com.example.LibraryManagementSystem.data.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.LibraryManagementSystem.data.rules.Validator
import com.example.LibraryManagementSystem.navigation.LibraryManagementAppRouter
import com.example.LibraryManagementSystem.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignupViewModel : ViewModel() {
    private val TAG = SignupViewModel::class.simpleName
    var signupUiState = mutableStateOf(SignupUiState())
    var allValidationPassed = mutableStateOf(false)
    var signUpInProgress = mutableStateOf(false)

    fun onEvent(event: SignupUiEvent) {
        validateDataWithRules()
        when (event) {
            is SignupUiEvent.FirstNameChanged -> {
                signupUiState.value = signupUiState.value.copy(
                    firstName = event.firstName
                )
                printState()
            }

            is SignupUiEvent.LastNameChanged -> {
                signupUiState.value = signupUiState.value.copy(
                    lastName = event.lastName
                )
                printState()
            }

            is SignupUiEvent.EmailChanged -> {
                signupUiState.value = signupUiState.value.copy(
                    email = event.email
                )
                printState()
            }

            is SignupUiEvent.PasswordChanged -> {
                signupUiState.value = signupUiState.value.copy(
                    password = event.password
                )
                printState()
            }

            is SignupUiEvent.RegistrationButtonClicked -> {
                signUp()
            }

            is SignupUiEvent.PrivacyPolicyCheckboxClicked -> {
                signupUiState.value = signupUiState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
        }
    }

    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()
        //taking email, pass from UI state because user input value saved there
        createUserInFirebase(
            email = signupUiState.value.email,
            password = signupUiState.value.password
        )
    }

    private fun validateDataWithRules() {
        //taking the result validation(error or not) based on validator rules
        val fNameResult = Validator.validateFirstName(fName = signupUiState.value.firstName)
        val lNameResult = Validator.validateLastName(lName = signupUiState.value.lastName)
        val emailResult = Validator.validateEmail(email = signupUiState.value.email)
        val passwordResult =
            Validator.validatePassword(password = signupUiState.value.password)
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = signupUiState.value.privacyPolicyAccepted
        )

        //output result on logcat
        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "FirstName = $fNameResult")
        Log.d(TAG, "LastName = $lNameResult")
        Log.d(TAG, "Email = $emailResult")
        Log.d(TAG, "Password = $passwordResult")
        Log.d(TAG, "Privacy Policy = $privacyPolicyResult")

        //saving the result on UI state (update global UI state)
        signupUiState.value = signupUiState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )

        //if all okay, then it will be true, register button will be worked, else it will not work (false)
        allValidationPassed.value =
            (fNameResult.status && lNameResult.status && emailResult.status
                    && passwordResult.status && privacyPolicyResult.status)
    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, signupUiState.value.toString())
    }

    //create user in firebase and passing the data form UI State where inputted data are saved
    private fun createUserInFirebase(email: String, password: String) {
        signUpInProgress.value = true
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside_addOnCompleteListener")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")

                signUpInProgress.value = false

                if (it.isSuccessful) {
                    LibraryManagementAppRouter.navigateTo(Screen.LogInScreen)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_addOnFailureListener")
                Log.d(TAG, "exception = ${it.message}")
                Log.d(TAG, "exception = ${it.localizedMessage}")
            }
    }

    /*fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        //checking logout successful
        val authStateListener = AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside sign-out success.")
                LibraryManagementAppRouter.navigateTo(Screen.LogInScreen)
            } else {
                Log.d(TAG, "Sign-out is not complete.")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }*/
}