package com.mgits.complaintreg

import android.annotation.SuppressLint
import android.app.DownloadManager.Query
import android.content.ClipData.Item
import android.icu.text.SymbolTable
import android.os.Build
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.navigation.AppNavHost
import com.mgits.complaintreg.ui.auth.LoginViewModel
import com.mgits.complaintreg.ui.theme.ComplaintRegAppTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

    @OptIn(DelicateCoroutinesApi::class, InternalCoroutinesApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComplaintRegAppTheme {
   AppNavHost()
                //Test()
            }
        }
    }
}

@Composable
fun Test() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {

        val nameRef = Firebase.firestore.collection("users")
            .whereEqualTo("admin", false)
            .get()
            .addOnSuccessListener { document ->
                for(i in document)
                Log.d(TAG, "work please " + i.getString("name"))
            }

       LazyColumn(
           modifier = Modifier
               .padding(8.dp)
               .fillMaxSize(),
           verticalArrangement = Arrangement.spacedBy(10.dp)

       ) {

       }
    }

}

@Composable
fun TextBox(name: String) {
    Text(text = name)
}

