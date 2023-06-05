package com.mits.cms.ui.auth.email_verification

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mits.cms.navigation.ROUTE_LOGIN
import com.mits.cms.ui.auth.register.RegisterViewModel
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun  EmailVerification(navController: NavController, viewModel: RegisterViewModel) {

    var expanded by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ){
            Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopEnd)) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.onSurface
                    )

                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                Firebase.auth.signOut()
                                navController.navigate(ROUTE_LOGIN) {
                                    popUpTo(0)
                                }
                            })
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    Firebase.auth.currentUser!!.sendEmailVerification()
                    viewModel.verifiedState(navController)
                    Toast.makeText(context, "Email verification send. Check mail", Toast.LENGTH_LONG).show()
                }) {
                    Text(text = "Send verification email")
                }
            }

        }



}


