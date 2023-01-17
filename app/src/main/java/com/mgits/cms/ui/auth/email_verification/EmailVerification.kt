package com.mgits.cms.ui.auth.email_verification

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.cms.navigation.ROUTE_USER_HOME
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun  EmailVerification(navController: NavController) {

    var emailState by remember {
        mutableStateOf(false)
    }
    suspend fun checkStatus() {
        while (true) {
            if(emailState)
                break
            emailState = Firebase.auth.currentUser!!.isEmailVerified
            Firebase.auth.currentUser!!.reload()
            delay(2000)
            Log.d(TAG, emailState.toString())
        }

    }

    Column(
        modifier =  Modifier
            .fillMaxSize()
    ){
        Button(onClick = {
            Firebase.auth.currentUser!!.sendEmailVerification()
            GlobalScope.launch {
                checkStatus()
            }
        }) {
            Text(text = "Send verification email")
        }
    }

    if(emailState) {
        navController.navigate(ROUTE_USER_HOME)
    }


}