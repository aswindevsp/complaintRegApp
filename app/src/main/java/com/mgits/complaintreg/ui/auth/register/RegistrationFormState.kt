package com.mgits.complaintreg.ui.auth.register

data class RegistrationFormState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String?  = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val department: String = "",
    val departmentError: String? = null
)
