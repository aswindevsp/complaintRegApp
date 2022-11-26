package com.mgits.complaintreg.ui.auth.register

import android.content.Context
import android.os.Build
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository(),
) : ViewModel() {


    var registerUiState by mutableStateOf(RegisterUiState())
        private set


    //For register
    fun onNameChangeSignup(nameSignUp: String) {
        registerUiState = registerUiState.copy(nameSignUp = nameSignUp)
    }
    fun onEmailChangeSignup(emailSignUp: String) {
        registerUiState = registerUiState.copy(emailSignUp = emailSignUp)
    }

    fun onPasswordChangeSignup(password: String) {
        registerUiState = registerUiState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String) {
        registerUiState = registerUiState.copy(confirmPasswordSignUp = password)
    }

    fun onDepartmentChangeSignup(departmentSignUp: String) {
        registerUiState = registerUiState.copy(departmentSignUp = departmentSignUp)
    }


    private fun validateSignupForm() =
        registerUiState.emailSignUp.isNotBlank() &&
                registerUiState.passwordSignUp.isNotBlank()


    @RequiresApi(Build.VERSION_CODES.R)
    fun createUser(context: Context, navController: NavController) = viewModelScope.launch {
        try {
            if (!validateSignupForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            registerUiState = registerUiState.copy(isLoading = true)
            if (registerUiState.passwordSignUp !=
                registerUiState.confirmPasswordSignUp
            ) {
                throw IllegalArgumentException(
                    "Password do not match"
                )
            }
            registerUiState = registerUiState.copy(signUpError = null)
            repository.createUser(
                registerUiState.emailSignUp,
                registerUiState.passwordSignUp
            ) { isSuccessful ->
                registerUiState = if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Register",
                        Toast.LENGTH_SHORT
                    ).show()
                    val db = Firebase.firestore
                    val uId = Firebase.auth.currentUser?.uid

                    val payload = hashMapOf(
                        "department" to registerUiState.departmentSignUp,
                        "email" to registerUiState.emailSignUp,
                        "name" to registerUiState.nameSignUp,
                        "admin" to false
                    )

                    if (uId != null) {
                        db.collection("users").document(uId)
                            .set(payload)
                            .addOnSuccessListener {
                                Toast.makeText(context, "It worked? Wah", Toast.LENGTH_LONG).show()
                                Log.d(TAG, "It should have worked")
                                navController.navigate("login")

                            }
                            .addOnFailureListener {(
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show())
                            }
                    }
                    registerUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed Register",
                        Toast.LENGTH_SHORT
                    ).show()
                    registerUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
            registerUiState = registerUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            registerUiState = registerUiState.copy(isLoading = false)
        }


    }


}

data class RegisterUiState(
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