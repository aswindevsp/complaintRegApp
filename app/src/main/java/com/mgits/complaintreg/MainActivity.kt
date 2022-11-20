package com.mgits.complaintreg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mgits.complaintreg.navigation.AppNavHost
import com.mgits.complaintreg.ui.auth.LoginViewModel
import com.mgits.complaintreg.ui.theme.ComplaintRegAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            ComplaintRegAppTheme {
                    AppNavHost(loginViewModel = loginViewModel)
            }
        }
    }
}

