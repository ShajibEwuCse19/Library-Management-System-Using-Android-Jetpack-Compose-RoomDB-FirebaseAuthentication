package com.example.LibraryManagementSystem.data.signup

sealed class SignupUiEvent {
    data class FirstNameChanged(val firstName: String): SignupUiEvent()
    data class LastNameChanged(val lastName: String): SignupUiEvent()
    data class EmailChanged(val email: String): SignupUiEvent()
    data class PasswordChanged(val password: String): SignupUiEvent()

    data class PrivacyPolicyCheckboxClicked(val status: Boolean) : SignupUiEvent()
    object RegistrationButtonClicked: SignupUiEvent()
}