package com.mgits.complaintreg.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mgits.complaintreg.ui.auth.LoginScreen
import com.mgits.complaintreg.ui.auth.LoginViewModel
import com.mgits.complaintreg.ui.auth.Register
import com.mgits.complaintreg.ui.home.UserHome
import com.mgits.complaintreg.ui.home.admin.AdminHome
import com.mgits.complaintreg.ui.home.admin.AdminHomeViewModel
import com.mgits.complaintreg.ui.home.UserHomeViewModel


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {

    val userHomeViewModel = viewModel(modelClass = UserHomeViewModel()::class.java)
    val loginViewModel = viewModel(modelClass = LoginViewModel()::class.java)

    val adminHomeViewModel = viewModel(modelClass = AdminHomeViewModel::class.java)
    val dataOrException = adminHomeViewModel.data.value

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(
                onNavToSignUpPage = {navController.navigate("register")},
                onNavToHomePage = { navController.navigate("user-home"){
                            popUpTo(ROUTE_LOGIN) { inclusive = true } } },
                onNavToAdminPage = {navController.navigate("admin-home"){
                    popUpTo(ROUTE_LOGIN) { inclusive = true } } },
                loginViewModel = loginViewModel
            )
        }
        composable(ROUTE_USER_HOME) {
            UserHome(userHomeViewModel, navController)
        }

        composable(ROUTE_ADMIN_HOME) {
            AdminHome(dataOrException)
        }

        composable(ROUTE_REGISTER) {
            Register(navController, loginViewModel)
        }


        
    }


}