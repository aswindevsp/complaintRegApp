package com.mgits.complaintreg

import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.navigation.AppNavHost

import com.mgits.complaintreg.ui.home.admin.AdminHomeViewModel
import com.mgits.complaintreg.ui.home.admin.loading.AdminLoadingScreen
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
//                MyContent();
//                AdminLoadingScreen(viewModel = adminHomeViewModel, navController = navController)
            }
        }
    }
}

// Creating a composable function
// to create an Outlined Text Field
// Calling this function as content
// in the above function
@Composable
fun MyContent(
) {
    val db = Firebase.firestore
    val collection = db.collection("complaints")
    val query = collection.whereEqualTo("complaintType", "Electrical")
    val countQuery = query.count()
    countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val snapshot = task.result
            Log.d(TAG, "Count: ${snapshot.count}")
        } else {
            Log.d(TAG, "Count failed: ", task.exception)
        }
    }
}

