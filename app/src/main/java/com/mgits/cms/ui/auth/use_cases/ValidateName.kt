package com.mgits.cms.ui.auth.use_cases

class ValidateName {
    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}
