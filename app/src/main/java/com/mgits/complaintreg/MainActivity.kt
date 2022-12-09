package com.mgits.complaintreg

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.mgits.complaintreg.navigation.AppNavHost
import com.mgits.complaintreg.ui.theme.ComplaintRegAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComplaintRegAppTheme {
   AppNavHost()
//LoginPage()
            }
        }
    }
}

// Creating a composable function
// to create an Outlined Text Field
// Calling this function as content
// in the above function
@Composable
fun LoginPage() {
    val options = listOf("Option 1", "Option 2", "Option 3")
    DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ }) {options

    }
}

