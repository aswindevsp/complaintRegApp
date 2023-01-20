package com.mgits.cms.ui.auth.register

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
import com.mgits.cms.navigation.ROUTE_EMAIL_VERIFICATION
import com.mgits.cms.repository.AuthRepository
import com.mgits.cms.ui.auth.use_cases.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val validateName: ValidateName = ValidateName(),
    private val validatePhone: ValidatePhone = ValidatePhone(),
    private val validateEmail: ValidateEmail = ValidateEmail(), //Change this to dependency injection in future
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateDepartment: ValidateDepartment = ValidateDepartment()
) : ViewModel() {


    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _isLoginSucess = MutableStateFlow(false)
    var isLoginSucess : StateFlow<Boolean> = _isLoginSucess

    @RequiresApi(Build.VERSION_CODES.R)
    fun onEvent(event: RegistrationFormEvent, navController: NavController, context: Context) {
        when (event) {
            is RegistrationFormEvent.NameChanged-> {
                state = state.copy(name = event.name)
            }
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.DepartmentChanged -> {
                state = state.copy(department = event.department)
            }
            is RegistrationFormEvent.PhoneNoChanged -> {
                state = state.copy(phoneNo = event.phoneNo)
            }
            is RegistrationFormEvent.Submit -> {
                submitData(navController, context)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun submitData(navController: NavController, context: Context) {
        val nameResult = validateName.execute(state.name)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val phoneNoResult = validatePhone.execute(state.phoneNo)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password, state.repeatedPassword
        )
        val departmentResult = validateDepartment.execute(state.department)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            departmentResult,
            phoneNoResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                departmentError = departmentResult.errorMessage,
                phoneNoError = phoneNoResult.errorMessage
            )
            return
        }

       viewModelScope.launch {
           repository.createUser(
               state.email,
               state.password
           ) { isSuccessful ->
               if (isSuccessful) {
                   Log.d(TAG, "asdf userCreate success")
                   val db = Firebase.firestore
                   val uId = Firebase.auth.currentUser?.uid

                   val payload = hashMapOf(
                       "department" to state.department,
                       "email" to state.email,
                       "name" to state.name,
                       "admin" to false,
                       "phoneNo" to state.phoneNo,
                   )

                   if (uId != null) {
                       db.collection("users").document(uId)
                           .set(payload)
                           .addOnSuccessListener {
                               navController.navigate(ROUTE_EMAIL_VERIFICATION){
                                   popUpTo(0)
                               }

                           }
                           .addOnFailureListener {
                               (
                                       Toast.makeText(context, "Wow! That shouldn't have happened. Contact admin.", Toast.LENGTH_LONG).show())
                           }
                   }

               } else {
                   Toast.makeText(context, "Account Already exists", Toast.LENGTH_LONG).show()

               }

           }
       }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}


