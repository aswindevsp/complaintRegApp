package com.mgits.complaintreg.ui.auth

import android.content.Context
import android.service.controls.ControlsProviderService
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.repository.AuthRepository
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository(),
) : ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean
        get() = repository.hasUser()


    var loginUiState by mutableStateOf(LoginUiState())
        private set


    //For login
    fun onEmailChange(email: String) {
        loginUiState = loginUiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }


    //For register
    fun onNameChangeSignup(nameSignUp: String) {
        loginUiState = loginUiState.copy(nameSignUp = nameSignUp)
    }
    fun onEmailChangeSignup(emailSignUp: String) {
        loginUiState = loginUiState.copy(emailSignUp = emailSignUp)
    }

    fun onPasswordChangeSignup(password: String) {
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String) {
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    fun onDepartmentChangeSignup(departmentSignUp: String) {
        loginUiState = loginUiState.copy(departmentSignUp = departmentSignUp)
    }


    fun validateEmail(): Boolean {
        val domain = loginUiState.email.split("@")
        return (domain.last() != "mgits.ac.in" && domain.size > 1)
    }

    private fun validateLoginForm() =
        loginUiState.email.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validateSignupForm() =
        loginUiState.emailSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank()


    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignupForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            if (loginUiState.passwordSignUp !=
                loginUiState.confirmPasswordSignUp
            ) {
                throw IllegalArgumentException(
                    "Password do not match"
                )
            }
            loginUiState = loginUiState.copy(signUpError = null)
            repository.createUser(
                loginUiState.emailSignUp,
                loginUiState.passwordSignUp
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Register",
                        Toast.LENGTH_SHORT
                    ).show()
                    val db = Firebase.firestore
                    val uId = Firebase.auth.currentUser?.uid

                    val payload = hashMapOf(
                        "department" to loginUiState.departmentSignUp,
                        "email" to loginUiState.emailSignUp,
                        "name" to loginUiState.nameSignUp,
                        "admin" to false
                    )


                    if (uId != null) {
                        db.collection("users").document(uId)
                            .set(payload)
                            .addOnSuccessListener {
                                Toast.makeText(context, "It worked? Wah", Toast.LENGTH_LONG).show()
                                Log.d(TAG, "It should have worked")
                            }
                            .addOnFailureListener {(
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show())
                            }
                    }
                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed Register",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }


    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.email,
                loginUiState.password
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Login Failed",
                        Toast.LENGTH_LONG
                    ).show()
                    loginUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }


    }

    fun resetPassword(context: Context) = viewModelScope.launch {
        try {
            repository.resetPassword(
                loginUiState.email
            ) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "Reset email send",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState.copy(isSuccessLogin = false)
                } else {
                    Toast.makeText(
                        context,
                        "Hmm this shouldn't have happened",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }


    }



}

data class LoginUiState(
    val email: String = "",
    val password: String = "",

    val nameSignUp: String = "",
    val emailSignUp: String = "",
    val passwordSignUp: String = "",
    val departmentSignUp: String = "",
    val confirmPasswordSignUp: String = "",

    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val isUserAdmin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null,
)