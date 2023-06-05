package com.mits.cms.ui.home.admin.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.mits.cms.navigation.ROUTE_ADMIN_HOME
import com.mits.cms.navigation.ROUTE_LOGIN
import com.mits.cms.components.LoadingAnimation
import com.mits.cms.ui.home.admin.AdminHomeViewModel


@Composable
fun AdminLoadingScreen(
    viewModel: AdminHomeViewModel,
    navController: NavHostController,
) {

    val isLoading by viewModel.isLoading.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
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