package com.mgits.complaintreg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mgits.complaintreg.ui.auth.LoginScreen
import com.mgits.complaintreg.ui.auth.LoginViewModel
import com.mgits.complaintreg.ui.home.UserHome

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    startDestination: String = ROUTE_LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(onNavToHomePage = {
                navController.navigate("user-home"){
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            }, loginViewModel = loginViewModel
            ) {}
        }
        composable(ROUTE_USER_HOME) {
            UserHome(navController)
        }


        
    }
}