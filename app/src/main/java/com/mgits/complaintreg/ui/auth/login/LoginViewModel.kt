package com.mgits.complaintreg.ui.auth.login

import android.content.Context
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository(),
) : ViewModel() {



    private val _isLoadingg = MutableStateFlow(false)
    var isLoadingg : StateFlow<Boolean> = _isLoadingg




    val hasUser: Boolean
        get() = repository.hasUser()

    var isAdminVal: Boolean = false

    fun isAdmin() {
        viewModelScope.launch {
            repository.isAdmin {
                isAdminVal = it;
            }
            delay(1200)
            onPress()
        }

    }

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onPress() {
        _isLoadingg.value = !_isLoadingg.value
    }



    //For login
    fun onEmailChange(email: String) {
        loginUiState = loginUiState.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }



    fun validateEmail(): Boolean {
        val domain = loginUiState.email.split("@")
        return (domain.last() != "mgits.ac.in" && domain.size > 1)
    }

    private fun validateLoginForm() =
        loginUiState.email.isNotBlank() &&
                loginUiState.password.isNotBlank()


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