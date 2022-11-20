package com.mgits.complaintreg.ui.auth


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mgits.complaintreg.R



@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToSignUpPage:() -> Unit,
) {

    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(
                    start = 64.dp,
                    end = 64.dp
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.mitslogo),
                contentDescription = "mits logo",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = "MITS Complaint Management System",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(
                    bottom = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                ),
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
        )
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
                    value = loginUiState?.userName ?: "",
                    onValueChange = {loginViewModel?.onUserNameChange(it)},
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = { Text(text = "Email")}
                )
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ){
//                    if (!isDomainValid && isEmailValid) {
//                        Text(
//                            text = "Require @mgits.ac.in",
//                            color = MaterialTheme.colors.error,
//                            style = MaterialTheme.typography.caption,
//                            modifier = Modifier.padding(start = 16.dp)
//                        )
//                    }
//                }


                TextField(
                    value = loginUiState?.password ?: "",
                    onValueChange = { loginViewModel?.onPasswordNameChange(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text(text = "******")}
                )

                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = "Forgot Password?",
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .clickable { loginViewModel?.resetPassword(context) }
                    )
                }
                Button(
                    onClick = {
                        loginViewModel?.loginUser(context)
                    },
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
        Row{
            Text(
                text = "Don't have an account? ",
                modifier = Modifier
                    .clickable {  }
            )

            Text(
                text = "Sign up",
                modifier = Modifier
                    .clickable {  },
                color = MaterialTheme.colors.primary
            )
        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }

    }

}
