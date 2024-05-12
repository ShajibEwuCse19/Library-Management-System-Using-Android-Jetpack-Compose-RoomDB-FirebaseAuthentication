package com.example.LibraryManagementSystem.data.rules

object Validator {
    fun validateFirstName(fName: String): ValidationResult {
        return ValidationResult(
            (fName.trim().isNotEmpty() && fName.length>=5)
        )
    }

    fun validateLastName(lName: String): ValidationResult {
        return ValidationResult(
            (lName.trim().isNotEmpty() && lName.length>=3)
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (email.trim().isNotEmpty())
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (password.trim().isNotEmpty() && password.length>=5)
        )
    }
    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidationResult {
        return ValidationResult(
            statusValue
        )
    }
}

data class ValidationResult(
    val status: Boolean = false
)