package com.mgits.complaintreg.ui.auth.use_cases

import android.util.Patterns
import androidx.navigation.navDeepLink
import java.util.regex.Pattern

class ValidateDepartment {

    fun execute(department: String): ValidationResult {
        val departmentList = listOf<String>("CE", "ME", "ECE", "EEE", "CS-A", "CS-B", "CS(AI)", "CS(AI&DS)", "CS(Cybersecurity)")
        if(department.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Please choose your department"
            )
        }
        if(!departmentList.contains(department)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid department, select department from the drop down menu"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}