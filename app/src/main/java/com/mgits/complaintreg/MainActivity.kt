package com.mgits.complaintreg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.navigation.AppNavHost
import com.mgits.complaintreg.ui.theme.ComplaintRegAppTheme

class MainActivity : ComponentActivity() {

    private val auth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComplaintRegAppTheme {
                    AppNavHost(auth)
            }
        }
    }
}

