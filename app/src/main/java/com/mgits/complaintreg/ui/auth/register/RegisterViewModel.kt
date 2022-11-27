package com.mgits.complaintreg.ui.auth.register

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
import com.mgits.complaintreg.ui.auth.use_cases.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val validateName: ValidateName = ValidateName(),
    private val validateEmail: ValidateEmail = ValidateEmail(), //Change this to dependency injection in future
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateDepartment: ValidateDepartment = ValidateDepartment()
) : ViewModel() {


    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

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
            is RegistrationFormEvent.Submit -> {
                submitData(navController, context)
            }
        }
    }

    private fun submitData(navController: NavController, context: Context) {
        val nameResult = validateName.execute(state.name)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password, state.repeatedPassword
        )
        val departmentResult = validateDepartment.execute(state.department)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            departmentResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                departmentError = departmentResult.errorMessage
            )
            return
        }

       viewModelScope.launch {
           repository.createUser(
               state.email,
               state.password
           ) { isSuccessful ->
               state = if (isSuccessful) {
                   Log.d(TAG, "asdf userCreate success")
                   val db = Firebase.firestore
                   val uId = Firebase.auth.currentUser?.uid

                   val payload = hashMapOf(
                       "department" to state.department,
                       "email" to state.email,
                       "name" to state.name,
                       "admin" to false
                   )

                   if (uId != null) {
                       db.collection("users").document(uId)
                           .set(payload)
                           .addOnSuccessListener {
                               Log.d(TAG, "asdf user datils added to db")
                               Log.d(TAG, "It should have worked")
                               navController.navigate("login")

                           }
                           .addOnFailureListener {
                               (
                                       Toast.makeText(context, "Wow! That shouldn't have happened. Contact admin.", Toast.LENGTH_LONG).show())
                           }
                   }
                   state.copy(isSuccessLogin = true)
               } else {
                   Toast.makeText(context, "Account Already exists", Toast.LENGTH_LONG).show()
                   state.copy(isSuccessLogin = false)
               }

           }
       }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}


