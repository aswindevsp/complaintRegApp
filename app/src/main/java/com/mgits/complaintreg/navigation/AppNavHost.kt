package com.mgits.complaintreg.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.mgits.complaintreg.FirebaseAuthApp
import com.mgits.complaintreg.ui.auth.LoginScreen
import com.mgits.complaintreg.ui.home.UserHome

@Composable
fun AppNavHost(
    auth: FirebaseAuth,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(auth, navController)
        }
        composable(ROUTE_USER_HOME) {
            UserHome(navController)
        }


        
    }
}