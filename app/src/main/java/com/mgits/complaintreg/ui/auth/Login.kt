package com.mgits.complaintreg.ui.auth


import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val isEmailValid by derivedStateOf {
        val domain = email.split('@').last()
        Log.d(TAG, "Domain = $domain")
        domain == "mgits.ac.in"
    }

    val isPasswordValid by derivedStateOf {
        password.length > 7
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(all = 10.dp)
            ) {
                TextField(
                    value = email,
                    onValueChange = {email = it},
                    label = { Text(text = "Email")},
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    isError = !isEmailValid,
                )
                if (isPasswordValid) {
                    Text(
                        text = "Error message",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }


                TextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = "Password")},
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    isError = !isPasswordValid
                )


                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 32.dp,
                            end = 32.dp
                        ),
                    shape = RoundedCornerShape(32.dp),

                ) {
                    Text(
                        text = "Login",
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }
            }

        }
        Text(
            text = "Create and Account",
            modifier = Modifier
                .clickable {  }
        )
    }

}

@Preview
@Composable
fun LoginScreenPreviewLight() {
        LoginScreen()
}