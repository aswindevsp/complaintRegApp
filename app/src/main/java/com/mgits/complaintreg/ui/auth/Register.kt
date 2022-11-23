package com.mgits.complaintreg.ui.auth

import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mgits.complaintreg.R
import kotlin.math.log

@Composable
fun Register(
    navController: NavController,
    loginViewModel: LoginViewModel? = null
){

    val loginUiState = loginViewModel?.loginUiState
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(vertical = 26.dp,
                horizontal = 12.dp
            )
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id= R.drawable.question),
            contentDescription ="",
            modifier = Modifier
                .size(30.dp)
        )
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier= Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id= R.drawable.arrowf),
            contentDescription ="",
            modifier = Modifier
                .size(84.dp)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        modifier = Modifier
            .padding(all = 10.dp)
    ) {
        /*Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp
                )){
        Image(
            painter = painterResource(id = R.drawable.mil),
            contentDescription = "mits logo",
            modifier= Modifier
                .fillMaxSize()
        )
        }*/
        Text(
            text="Sign Up",
            fontWeight = FontWeight.Medium,
            fontSize= 20.sp,
            modifier = Modifier
                .height(44.dp)

        )
        Text(
            text ="Create an account in order to file a complaint",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier= Modifier
                .height(34.dp)
        )
        Card(
            elevation = 0.dp,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier=Modifier
                    .padding(all=10.dp)
            )
            {
                OutlinedTextField(
                    value = loginUiState?.nameSignUp?:"",
                    onValueChange = { loginViewModel?.onNameChangeSignup(it)},
                    label = {
                        Text(
                            text = "Name",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                OutlinedTextField(
                    value = loginUiState?.emailSignUp?:"",
                    onValueChange = { loginViewModel?.onEmailChangeSignup(it) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                    label = {
                        Text(
                            text = "Email",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )

                OutlinedTextField(
                    value = loginUiState?.passwordSignUp?:"",
                    onValueChange = {loginViewModel?.onPasswordChangeSignup(it)},
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password") },
                    label = {
                        Text(
                            text = "Password",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                OutlinedTextField(
                    value = loginUiState?.confirmPasswordSignUp?:"",
                    onValueChange = {loginViewModel?.onConfirmPasswordChange(it)},
                    label = {
                        Text(
                            text = "Confirm Password",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )

                Divider(
                    modifier = Modifier
                        .padding(all=5.dp)
                )
                OutlinedTextField(
                    value = loginUiState?.departmentSignUp?:"",
                    onValueChange = { loginViewModel?.onDepartmentChangeSignup(it)},
                    label = {
                        Text(
                            text = "Department",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )

                Divider(
                    modifier = Modifier
                        .padding(all=5.dp)
                )
                Text(
                    text = "*The email that is entered should be of an official mgits account",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
                Button(
                    onClick = {
                        loginViewModel?.createUser(context, navController) },
                    shape = RoundedCornerShape(12.dp),
                    modifier= Modifier
                        .padding(all = 12.dp)
                        .fillMaxWidth()
                        .height(42.dp)
                )
                {
                    Text(text = "Register")
                }



            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Already have an account?",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
            ClickableText(
                text = AnnotatedString("Log in"),
                onClick = {navController.navigate("login") },
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Red
                )
            )
        }
    }
}

