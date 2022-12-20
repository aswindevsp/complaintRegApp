package com.mgits.cms.ui.home.user.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
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
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(80.dp)
                    )
                }

                viewModel.userDetails.value.data?.let {
                    Text(
                        text = it.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }

                viewModel.userDetails.value.data?.let {
                    Text(
                        text = it.email,
                        fontSize = 16.sp,
                        color = Color.Gray
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
                    onClick = { TODO() }
                )

                ProfileCard(
                    text = "Rate App",
                    icon = Icons.Default.RateReview,
                    onClick = { TODO() }
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
                Icon(imageVector = Icons.Default.Close, contentDescription = "Go Back")
            }
            Text(
                text = "Settings",
                modifier = Modifier
                    .padding(start = 20.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        shape = RoundedCornerShape(0.dp)
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
                    .padding(end = 20.dp)
            )
            Text(
                text = text
            )
        }
    }
}


