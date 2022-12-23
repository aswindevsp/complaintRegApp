package com.mgits.cms.ui.auth.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mgits.cms.R
import com.mgits.cms.navigation.ROUTE_RESET_PASSWORD
import com.mgits.cms.ui.home.admin.CircularProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToAdminPage:() -> Unit,
    onNavToSignUpPage:() -> Unit,
) {

    val loginUiState = loginViewModel?.loginUiState
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current


    // Creating a variable to store toggle state
    var passwordVisible by remember { mutableStateOf(false) }

    val loadingState = loginViewModel?.isLoading?.collectAsState()

    if(loginViewModel?.hasUser == true) {
        loginViewModel.isAdmin()
        if (loadingState != null) {
            if(loadingState.value) {
                if (loginViewModel.isAdminVal)
                    onNavToAdminPage.invoke()
                else
                    onNavToHomePage.invoke()
            } else {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoadingAnimation()
                }
            }
        }


    }
    else
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
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
            elevation = 0.dp,
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(all = 10.dp)
            ) {
                OutlinedTextField(
                    value = loginUiState?.email ?: "",
                    onValueChange = {loginViewModel?.onEmailChange(it)},
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
//                    placeholder = { Text(text = "abc@mgits.ac.in")},
                    label = { Text(text = "Email")},
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    if (loginViewModel != null) {
                        if (loginViewModel.validateEmail()) {
                            Text(
                                text = "Require @mgits.ac.in",
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }


                OutlinedTextField(
                    value = loginUiState?.password ?: "",
                    onValueChange = { loginViewModel?.onPasswordChange(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "lockIcon") },
                    keyboardActions = KeyboardActions(onDone = {
                        loginViewModel?.loginUser(context)
                        keyboardController?.hide()
                    }),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    label = {Text(text = "Password")},
                    trailingIcon = {
                        if (loginUiState != null) {
                            if(loginUiState.password.isNotBlank()) {
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
                    }

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
                            .clickable { navController.navigate(ROUTE_RESET_PASSWORD) }
                    )
                }
                Button(
                    onClick = {
                        loginViewModel?.loginUser(context)
                        if (loginUiState != null) {
                            if(loginUiState.isSuccessLogin){
                                loginViewModel.isAdmin()

                                if (loginViewModel.isAdminVal)
                                    onNavToAdminPage.invoke()
                                else
                                    onNavToHomePage.invoke()
                            }
                        }
                    },
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
                    if (!loginUiState!!.isLoading) {
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
                                circleColor = MaterialTheme.colors.background,
                                circleSize = 15.dp
                            )
                        }
                    }
                }
            }

        }
        Row{
            Text(
                text = "Don't have an account? "
            )

            Text(
                text = "Sign up",
                modifier = Modifier
                    .clickable { onNavToSignUpPage.invoke() },
                color = MaterialTheme.colors.primary
            )
        }
    }

}
