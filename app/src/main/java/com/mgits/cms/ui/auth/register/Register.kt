package com.mgits.cms.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mgits.cms.navigation.ROUTE_LOGIN
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    navController: NavController,
    viewModel: RegisterViewModel? = null,
) {
    val state = viewModel?.state
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)){

        TopAppBar(
            modifier = Modifier,
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            title = { },
            navigationIcon = {
                IconButton(onClick = {  navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = { (navController.navigate(ROUTE_LOGIN)) }) {
                    Icon(
                        imageVector = Icons.Filled.Help,
                        contentDescription = "Help",
                    )
                }
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
           // verticalArrangement = Arrangement.Center,

            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            Text(
                text = "MITS CMS".uppercase(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Create New Account",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Card(
                elevation = CardDefaults.cardElevation(0.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(all = 10.dp)
                )
                {
                    if (state != null) {
                        OutlinedTextField(
                            value = state.name,
                            singleLine = true,
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
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "emailIcon"
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
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                    }


                    if (state != null) {
                        OutlinedTextField(
                            singleLine = true,
                            value = state.email,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegistrationFormEvent.EmailChanged(it),
                                    navController,
                                    context
                                )
                            },
                            isError = state.emailError != null,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "emailIcon"
                                )
                            },
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
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                    }




                    if (state != null) {
                        OutlinedTextField(
                            singleLine = true,
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
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "emailIcon"
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
                        if (state.passwordError != null) {
                            Text(
                                text = state.passwordError,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                    }

                    if (state != null) {
                        OutlinedTextField(
                            singleLine = true,
                            value = state.repeatedPassword,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegistrationFormEvent.RepeatedPasswordChanged(it),
                                    navController,
                                    context
                                )
                            },
                            isError = state.repeatedPasswordError != null,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "emailIcon"
                                )
                            },
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
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                    }




                    if (state != null) {
                        OutlinedTextField(
                            singleLine = true,
                            value = state.phoneNo,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegistrationFormEvent.PhoneNoChanged(it),
                                    navController,
                                    context
                                )
                            },
                            isError = state.phoneNoError != null,
                            label = {
                                Text(
                                    text = "Phone Number ",
                                    fontSize = 15.sp,
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "phoneIcon"
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                        )
                    }
                    if (state != null) {
                        if (state.phoneNoError != null) {
                            Text(
                                text = state.phoneNoError,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                    }





                    Divider(
                        modifier = Modifier
                            .padding(all = 5.dp)
                    )

                    if (state != null) {
                        val optoins = listOf(
                            "CS",
                            "AI&DS",
                            "CE",
                            "ME",
                            "ECE",
                            "EEE"
                        )
                        var expanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange ={expanded = !expanded} ) {
                            OutlinedTextField(
                                singleLine = true,
                                value = state.department,
                                readOnly = true,

                                onValueChange = {  },
                                isError = state.passwordError != null,
                                label = {
                                    Text(
                                        text = "Department",
                                        fontSize = 15.sp,
                                    )
                                },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .menuAnchor()
                            )
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                optoins.forEach {
                                    DropdownMenuItem(text = { Text(text = it)},
                                        onClick = {
                                            expanded = false
                                            viewModel.onEvent(
                                                RegistrationFormEvent.DepartmentChanged(it),
                                                navController,
                                                context
                                            )
                                        })
                                }
                            }
                        }

                    }

                    if (state != null) {
                        if (state.departmentError != null) {
                            Text(
                                text = state.departmentError,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 11.sp
                            )
                        }
                    }



                    Divider(
                        modifier = Modifier
                            .padding(all = 5.dp)
                    )
                    Button(
                        onClick = {
                            viewModel?.onEvent(RegistrationFormEvent.Submit, navController, context)
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
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
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ClickableText(
                    text = AnnotatedString("Log in"),
                    onClick = { navController.popBackStack() },
                    style = TextStyle(
                        //fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }

}