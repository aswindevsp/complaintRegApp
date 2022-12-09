package com.mgits.complaintreg.ui.auth.use_cases

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if(password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter a password"
            )
        }
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain at least 8 characters"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}