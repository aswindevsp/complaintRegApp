package com.mgits.complaintreg.ui.auth.reset_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.mgits.complaintreg.ui.auth.login.LoginViewModel


@Composable
fun ResetPassword(viewModel: LoginViewModel,navController:NavController) {
    val context = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter your email") })

        Button(onClick = { viewModel.resetPassword(email, context) }) {
            Text(text = "Enter")
        }

    }
}


