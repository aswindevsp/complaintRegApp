package com.mgits.complaintreg

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
//Test()
//LoginPage()
            }
        }
    }
}
@Composable
fun Test() {
    Firebase.firestore
        .collection("complaints")
        .document("JdmwPefgcVX0MxCdNCJ9")
        .update("status", "updated")

}
