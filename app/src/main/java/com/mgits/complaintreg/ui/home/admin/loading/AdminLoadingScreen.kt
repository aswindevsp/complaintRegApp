package com.mgits.complaintreg.ui.home.admin.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mgits.complaintreg.navigation.ROUTE_ADMIN_HOME
import com.mgits.complaintreg.navigation.ROUTE_LOGIN
import com.mgits.complaintreg.ui.auth.login.LoadingAnimation
import com.mgits.complaintreg.ui.home.admin.AdminHomeViewModel


@Composable
fun AdminLoadingScreen(
    viewModel: AdminHomeViewModel,
    navController: NavHostController,
) {

    val isLoading by viewModel.isLoading.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isLoading) {
                LoadingAnimation()
        } else{
            navController.navigate(ROUTE_ADMIN_HOME){
                popUpTo(ROUTE_LOGIN) { inclusive = true }
            }
        }
    }
}