package com.mgits.complaintreg.ui.home.user

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.navigation.ROUTE_LOGIN
import com.mgits.complaintreg.navigation.ROUTE_USER_HOME

@Composable
fun UserDrawer(navController: NavController) {
    Button(onClick =
    {
        Firebase.auth.signOut()
        navController.navigate(ROUTE_LOGIN)
    }) {
        Text(text = "LogOut");
    }
}