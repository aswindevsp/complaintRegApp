package com.mits.cms.ui.auth.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mits.cms.R
import com.mits.cms.components.LoadingAnimation
import com.mits.cms.navigation.ROUTE_RESET_PASSWORD


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    onNavToSignUpPage: () -> Unit
) {

    val loginUiState = loginViewModel.loginUiState
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


    // Creating a variable to store toggle state
    var passwordVisible by remember { mutableStateOf(false) }




    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
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
                painter = painterResource(id = R.drawable.mits_logo),
                contentDescription = "mits logo",
                modifier = Modifier
                    .padding(16.dp)
                    .scale(1.3f)
                    .fillMaxSize()
            )
        }
        Text(
            text = "Maintenance And Complaint Management System",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(
                    bottom = 16.dp,
                    start = 32.dp,
                    end = 32.dp
                ),
            textAlign = TextAlign.Center,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(all = 10.dp)
            ) {
                OutlinedTextField(
                    value = loginUiState.email ,
                    onValueChange = { loginViewModel.onEmailChange(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
//                    placeholder = { Text(text = "abc@mgits.ac.in")},
                    label = { Text(text = "Email") },
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (loginViewModel.validateEmail()) {
                        Text(
                            text = "Require @mgits.ac.in",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }


                OutlinedTextField(
                    value = loginUiState.password ,
                    onValueChange = { loginViewModel.onPasswordChange(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "lockIcon"
                        )
                    },
                    keyboardActions = KeyboardActions(onDone = {
                        loginViewModel.loginUser(context, navController)
                        keyboardController?.hide()
                    }),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    label = { Text(text = "Password") },
                    trailingIcon = {
                        if (loginUiState.password.isNotBlank()) {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Localized description for accessibility services
                            val description =
                                if (passwordVisible) "Hide password" else "Show password"

                            // Toggle button to hide or display password
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, description)
                            }
                        }
                    }

                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Forgot Password?",
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable { navController.navigate(ROUTE_RESET_PASSWORD) }
                    )
                }
                Button(
                    onClick = {loginViewModel.loginUser(context, navController) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(
                            top = 16.dp,
                            start = 32.dp,
                            end = 32.dp
                        ),
                    shape = RoundedCornerShape(32.dp),
                    ) {
                    if (!loginUiState.isLoading) {
                        Text(
                            text = "Login",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    } else {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            LoadingAnimation(
                                circleColor = MaterialTheme.colorScheme.surface,
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                circleSize = 15.dp
                            )
                        }
                    }
                }
            }

        }
        Row {
            Text(
                text = "Don't have an account? ",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Sign up",
                modifier = Modifier
                    .clickable { onNavToSignUpPage.invoke() },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
