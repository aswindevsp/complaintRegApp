package com.mgits.complaintreg.ui.auth.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mgits.complaintreg.R


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun Register(
    navController: NavController,
    viewModel: RegisterViewModel? = null
) {
    val state = viewModel?.state
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(
                vertical = 26.dp,
                horizontal = 12.dp
            )
            .fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")

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
                if (state != null) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = {
                            viewModel.onEvent(
                                RegistrationFormEvent.NameChanged(it),
                                navController,
                                context
                            )
                        },
                        isError = state.emailError != null,
                        label = {
                            Text(
                                text = "Name",
                                fontSize = 15.sp,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )
                    if (state.nameError != null) {
                        Text(
                            text = state.nameError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                }


                if (state != null) {
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = {
                            viewModel.onEvent(
                                RegistrationFormEvent.EmailChanged(it),
                                navController,
                                context
                            )
                        },
                        isError = state.emailError != null,
                        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
                        label = {
                            Text(
                                text = "Email",
                                fontSize = 15.sp,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        )
                    )
                    if (state.emailError != null) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                }




                if (state != null) {
                    OutlinedTextField(
                        value = state.password,

                        onValueChange = {
                            viewModel.onEvent(
                                RegistrationFormEvent.PasswordChanged(it),
                                navController,
                                context
                            )
                        },
                        isError = state.passwordError != null,
                        label = {
                            Text(
                                text = "Password",
                                fontSize = 15.sp,
                            )
                        },
                        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "emailIcon") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    )
                }
                if (state != null) {
                    if (state.passwordError != null) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                }

                if (state != null) {
                    OutlinedTextField(
                        value = state.repeatedPassword,
                        onValueChange = {
                            viewModel.onEvent(
                                RegistrationFormEvent.RepeatedPasswordChanged(it),
                                navController,
                                context
                            )
                        },
                        isError = state.repeatedPasswordError != null,
                        label = {
                            Text(
                                text = "Confirm Password",
                                fontSize = 15.sp,
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    )
                }
                if (state != null) {
                    if (state.repeatedPasswordError != null) {
                        Text(
                            text = state.repeatedPasswordError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                }


                Divider(
                    modifier = Modifier
                        .padding(all=5.dp)
                )

                if (state != null) {
                    ExpandedDropDown(
                        modifier = Modifier
                            .fillMaxWidth(),
                        listOfItems = listOf("CE", "ME", "ECE", "EEE", "CS-A", "CS-B", "CS(AI)", "CS(AI&DS)", "CS(Cybersecurity)"),
                        enable = true,
                        parentTextFieldCornerRadius = 5.dp,
                        placeholder = "Department",
                        dropdownItem = {name ->
                            Text(text = name)
                        },
                        onDropDownItemSelected = {
                            viewModel.onEvent(
                                RegistrationFormEvent.DepartmentChanged(it),
                                navController,
                                context
                            )
                        },
                        isError = state.departmentError != null,
                    )
                }

                if (state != null) {
                    if (state.departmentError != null) {
                        Text(
                            text = state.departmentError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start),
                            fontSize = 11.sp
                        )
                    }
                }



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
                        viewModel?.onEvent(RegistrationFormEvent.Submit, navController, context)
                    },
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