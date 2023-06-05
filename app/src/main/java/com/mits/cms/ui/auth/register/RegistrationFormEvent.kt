package com.mits.cms.ui.auth.register

sealed class RegistrationFormEvent{
    data class NameChanged(val name: String) : RegistrationFormEvent()
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegistrationFormEvent()
    data class DepartmentChanged(val department: String) : RegistrationFormEvent()
    data class PhoneNoChanged(val phoneNo: String): RegistrationFormEvent()

    object Submit: RegistrationFormEvent()
}
