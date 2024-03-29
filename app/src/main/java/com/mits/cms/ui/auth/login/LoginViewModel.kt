package com.mits.cms.ui.auth.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mits.cms.navigation.ROUTE_ADMIN_HOME
import com.mits.cms.navigation.ROUTE_LOADING
import com.mits.cms.navigation.ROUTE_USER_HOME
import com.mits.cms.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository(),
) : ViewModel() {


    var isAdminVal: Boolean = false

    private val _isLoading = MutableStateFlow(false)
    var isLoading : StateFlow<Boolean> = _isLoading

    private fun onPress() {
        _isLoading.value = !_isLoading.value
    }


    fun isAdmin(navController: NavController) {
        viewModelScope.launch {
            repository.isAdmin {
                isAdminVal = it
            }
            if(isAdminVal)
                navController.navigate(ROUTE_ADMIN_HOME)
            else
                navController.navigate(ROUTE_USER_HOME)
            delay(1200)
            onPress()
        }

    }


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
    fun resetPassword(email: String, context: Context) {

        if(email.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Reset email send",
                            Toast.LENGTH_SHORT
                        ).show()// Password reset email sent
                        // ...
                    } else {
                        Toast.makeText(
                            context,
                            "Invalid Email",
                            Toast.LENGTH_SHORT
                        ).show()// Error occurred, handle the error
                        // ...
                    }
                }
        } else {
            Toast.makeText(context, "No email entered", Toast.LENGTH_SHORT).show()
        }
    }
    fun validateEmail(): Boolean {
        val domain = loginUiState.email.split("@")
        return (domain.last() != "mgits.ac.in" && domain.size > 1)
    }

    private fun validateLoginForm() =
        loginUiState.email.isNotBlank() &&
                loginUiState.password.isNotBlank()


    fun loginUser(context: Context, navController: NavController) = viewModelScope.launch {
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
                    navController.navigate(ROUTE_LOADING) {
                        popUpTo(0)
                    }
                    loginUiState.copy(isSuccessLogin = true)


                } else {
                    Toast.makeText(
                        context,
                        "Invalid Login Credentials",
                        Toast.LENGTH_LONG
                    ).show()
                    loginUiState.copy(isSuccessLogin = false)
                }

            }


        } catch (e: Exception) {
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            delay(500)
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