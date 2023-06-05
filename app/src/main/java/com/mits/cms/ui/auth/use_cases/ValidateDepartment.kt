package com.mits.cms.ui.auth.use_cases

class ValidateDepartment {

    fun execute(department: String): ValidationResult {
        val departmentList = listOf<String>( "CS",
            "AI&DS",
            "CE",
            "ME",
            "ECE",
            "EEE")
        if(department.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please choose your department"
            )
        }
        if(!departmentList.contains(department)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please select your department"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}