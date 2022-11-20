package com.mgits.complaintreg.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun UserHome(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Hello World")
    }
}