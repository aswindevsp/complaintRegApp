package com.mgits.complaintreg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.mgits.complaintreg.navigation.AppNavHost
import com.mgits.complaintreg.ui.theme.ComplaintRegAppTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComplaintRegAppTheme {

   AppNavHost()
//                Test();
            }
        }
    }
}

@Composable
fun Test() {
    var a by remember {
        mutableStateOf("")
    }

    a = Firebase.firestore.collection("complaints").document().id
    
    Text(text = a)
}