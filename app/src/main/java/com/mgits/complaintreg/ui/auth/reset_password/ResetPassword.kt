package com.mgits.complaintreg.ui.auth.reset_password

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.mgits.complaintreg.ui.auth.login.LoginViewModel

@Composable
fun ResetPassword(viewModel: LoginViewModel, navController: NavController) {

    val context = LocalContext.current
    val state = viewModel.loginUiState
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") }
        )
        OutlinedButton(onClick = {
            viewModel.resetPassword(context)
        }) {
            Text("Reset Password")
        }
    }
}


