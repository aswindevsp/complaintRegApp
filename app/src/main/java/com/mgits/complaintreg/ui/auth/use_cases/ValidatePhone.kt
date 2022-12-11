package com.mgits.complaintreg.ui.auth.use_cases;

class ValidatePhone {
    fun execute(phoneNo: String): ValidationResult {
        if(phoneNo.isBlank()) {
            return ValidationResult(
                    successful = false,
                    errorMessage = "Please enter a phone number"
            )
        }
        if(phoneNo.length !=10) {
            return ValidationResult(
                    successful = false,
                    errorMessage = "Invalid phone number"
            )
        }

        return ValidationResult(
                successful = true
        )
    }
}
