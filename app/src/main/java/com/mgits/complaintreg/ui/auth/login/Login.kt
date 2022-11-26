package com.mgits.complaintreg.ui.auth


import androidx.compose.foundation.*
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.R



@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToAdminPage:() -> Unit,
    onNavToSignUpPage:() -> Unit,
) {

    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current


    LaunchedEffect(key1 = loginViewModel?.hasUser){
        if (loginViewModel?.hasUser == true){
            val test = Firebase.auth.currentUser?.uid
            val db = Firebase.firestore

            if (test != null) {
                db.collection("users").document(test)
                    .get()
                    .addOnSuccessListener { idk->
                        if(idk.getBoolean("admin") == true)
                            onNavToAdminPage.invoke()
                        else
                            onNavToHomePage.invoke()
                    }
            }

        }
    }

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
                painter = painterResource(id = R.drawable.mil),
                contentDescription = "mits logo",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                //contentScale = ContentScale.Crop
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
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = { Text(text = "abc@mgits.ac.in")},
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
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    placeholder = { Text(text = "******")},
                    label = {Text(text = "Password")}

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
                text = "Don't have an account? "
            )

            Text(
                text = "Sign up",
                modifier = Modifier
                    .clickable { onNavToSignUpPage.invoke() },
                color = MaterialTheme.colors.primary
            )
        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }



    }

}
