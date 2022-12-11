package com.mgits.complaintreg.ui.home.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.navigation.ROUTE_LOGIN

@Composable
fun UserDrawer(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = {
                Firebase.auth.signOut()
                navController.navigate(ROUTE_LOGIN) {
                    navOptions {
                        popUpTo(ROUTE_LOGIN)
                        launchSingleTop = true
                    }
                }
            } ) {
            Text(text = "Sign Out")
        }
    }
}