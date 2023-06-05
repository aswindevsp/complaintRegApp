package com.mits.cms.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mits.cms.ui.auth.email_verification.EmailVerification
import com.mits.cms.ui.auth.login.LoginScreen
import com.mits.cms.ui.auth.login.LoginViewModel
import com.mits.cms.ui.auth.register.Register
import com.mits.cms.ui.auth.register.RegisterViewModel
import com.mits.cms.ui.auth.reset_password.ResetPassword
import com.mits.cms.ui.home.admin.AdminHome
import com.mits.cms.ui.home.admin.AdminHomeViewModel
import com.mits.cms.ui.home.admin.details.DetailedView
import com.mits.cms.ui.home.admin.loading.AdminLoadingScreen
import com.mits.cms.ui.home.user.UserComplaintHistory
import com.mits.cms.ui.home.user.UserDetailedView
import com.mits.cms.ui.home.user.UserHome
import com.mits.cms.ui.home.user.UserHomeViewModel
import com.mits.cms.ui.home.user.profile.Profile
import com.mits.cms.ui.home.user.profile.ProfileViewModel
import com.mits.cms.ui.loading.Loading


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    var startDestination: String = ROUTE_LOADING
    val userHomeViewModel = viewModel(modelClass = UserHomeViewModel::class.java)
    val registerViewModel = viewModel(modelClass = RegisterViewModel()::class.java)
    val adminHomeViewModel = viewModel(modelClass = AdminHomeViewModel::class.java)
    val profileViewModel = viewModel(modelClass = ProfileViewModel::class.java)
    val dataOrException = adminHomeViewModel.data.value

    val loginViewModel = viewModel(modelClass = LoginViewModel()::class.java)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(
                navController,
                loginViewModel = loginViewModel,
            ) { navController.navigate("register") }
        }
        composable(ROUTE_USER_PROFILE) {
            Profile(
                onNavToLoginPage = {navController.navigate(ROUTE_LOGIN){
                    popUpTo(0)}},
                viewModel = profileViewModel,
                navController
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

        composable(ROUTE_EMAIL_VERIFICATION) {
            EmailVerification(navController, registerViewModel)
        }
        
        composable(ROUTE_LOADING) {
            Loading(navController)
        }

        composable(ROUTE_USER_COMPLAINTS) {
            UserComplaintHistory(userHomeViewModel, navController)
        }

        composable(ROUTE_USER_COMPLAINT_DETAILS) {
            UserDetailedView(navController = navController, viewModel = userHomeViewModel)
        }



    }





}