package com.mgits.complaintreg.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mgits.complaintreg.ui.auth.login.LoginScreen
import com.mgits.complaintreg.ui.auth.login.LoginViewModel
import com.mgits.complaintreg.ui.auth.register.Register
import com.mgits.complaintreg.ui.auth.register.RegisterViewModel
import com.mgits.complaintreg.ui.auth.reset_password.ResetPassword
import com.mgits.complaintreg.ui.home.user.UserHome
import com.mgits.complaintreg.ui.home.admin.AdminHome
import com.mgits.complaintreg.ui.home.admin.AdminHomeViewModel
import com.mgits.complaintreg.ui.home.user.UserHomeViewModel
import com.mgits.complaintreg.ui.home.admin.details.DetailedView
import com.mgits.complaintreg.ui.home.admin.loading.AdminLoadingScreen


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    var startDestination: String = ROUTE_LOGIN
    val userHomeViewModel = viewModel(modelClass = UserHomeViewModel()::class.java)
    val loginViewModel = viewModel(modelClass = LoginViewModel()::class.java)
    val registerViewModel = viewModel(modelClass = RegisterViewModel()::class.java)

    val adminHomeViewModel = viewModel(modelClass = AdminHomeViewModel::class.java)
    val dataOrException = adminHomeViewModel.data.value



    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(navController,
                onNavToSignUpPage = {navController.navigate("register")},
                onNavToHomePage = { navController.navigate("user-home"){
                            popUpTo(ROUTE_LOGIN) { inclusive = true } } },
                onNavToAdminPage = {navController.navigate("admin-home"){
                    popUpTo(ROUTE_LOGIN) { inclusive = true } } },
                loginViewModel = loginViewModel,
            )
        }
        composable(ROUTE_USER_HOME) {
            UserHome(userHomeViewModel, navController)
        }

        composable(ROUTE_ADMIN_HOME) {
            AdminHome(dataOrException, navController, adminHomeViewModel)
        }

        composable(ROUTE_REGISTER) {
            Register(navController, registerViewModel)
        }

        composable(ROUTE_ADMIN_DET) {
            DetailedView(navController, adminHomeViewModel)
        }

        composable(ROUTE_ADMIN_LOADING) {
            AdminLoadingScreen(adminHomeViewModel, navController)
        }

        composable(ROUTE_RESET_PASSWORD) {
            ResetPassword(loginViewModel, navController)
        }

    }





}