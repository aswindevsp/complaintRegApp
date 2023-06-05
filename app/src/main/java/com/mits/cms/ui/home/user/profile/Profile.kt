package com.mits.cms.ui.home.user.profile


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun Profile(
    onNavToLoginPage:() -> Unit,
    viewModel: ProfileViewModel,
    navController: NavController
) {

    val scrollState = rememberScrollState(0)
    val context = LocalContext.current

    viewModel.getUserDetails()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        TopBar(navController = navController)
        Column {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                viewModel.userDetails.value.data?.let {
                    Text(
                        text = it.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                viewModel.userDetails.value.data?.let {
                    Text(
                        text = it.email,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 20.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
            ) {

                ProfileCard(
                    text = "Help",
                    icon = Icons.Default.HelpCenter,
                    onClick = { Toast.makeText(context, "Not yet implemented", Toast.LENGTH_SHORT).show() }
                )

                ProfileCard(
                    text = "Rate App",
                    icon = Icons.Default.RateReview,
                    onClick = { Toast.makeText(context, "Not yet implemented", Toast.LENGTH_SHORT).show() }
                )

                ProfileCard(
                    text = "Sign Out",
                    icon = Icons.Default.Logout,
                    onClick = {
                        viewModel.signOut()
                        onNavToLoginPage.invoke()
                    }
                )

            }
        }
    }
}


@Composable
fun TopBar(navController: NavController) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment =  Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Go Back", tint = MaterialTheme.colorScheme.onSurface)
            }
            Text(
                text = "Settings",
                modifier = Modifier
                    .padding(start = 20.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}



@Composable
fun ProfileCard(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Logout",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 20.dp),
                tint = Color.Black
            )
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


