package com.mgits.complaintreg

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Size
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kanyidev.searchable_dropdown.SearchableExpandedDropDownMenu

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
//                MyContent();
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
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally
    ){
        val sports =
            mutableListOf("CS A", "CS B", "CS AI", "AI DS", "Civil", "Mechanical", "EC", "EEE")

        SearchableExpandedDropDownMenu(
            listOfItems = sports,
            colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black),
            placeholder = "Department",
            dropdownItem = {
                Row {
                    Text(
                        text = it,
                        modifier = Modifier
                    )
                }

            }
        )
    }
}

