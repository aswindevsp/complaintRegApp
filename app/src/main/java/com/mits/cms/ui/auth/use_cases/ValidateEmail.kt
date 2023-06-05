package com.mits.cms.ui.auth.use_cases

import android.util.Patterns

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter your email"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ;return ValidationResult(
                successful = false,
                errorMessage = "Please enter a valid email"
            )
        }
        if(email.split('@').last() != "mgits.ac.in") {
            return ValidationResult(
                successful = false,
                errorMessage = "Please enter your college mail"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}