package com.mgits.complaintreg.ui.home.user


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.R
import com.mgits.complaintreg.navigation.ROUTE_LOGIN
import com.mgits.complaintreg.ui.auth.register.ExpandedDropDown
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate


@Composable
fun UserHome(
    homeViewModel: UserHomeViewModel? = null,
    navController: NavController
){

    val homeUiState = homeViewModel?.homeUiState

    val context = LocalContext.current

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState= rememberMaterialDialogState()
    var c by remember {
        mutableStateOf(0)
    }
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(
                vertical = 26.dp,
                horizontal = 12.dp
            )
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id= R.drawable.question),
            contentDescription ="",
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    Firebase.auth.signOut();
                    navController.navigate(ROUTE_LOGIN)
                }
        )
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(
                vertical = 26.dp,
                horizontal = 20.dp
            )
            .fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Default.Menu,
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        modifier = Modifier
            .padding(all = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp
                )){
//            Image(
//                painter = painterResource(id = R.drawable.mila),
//                contentDescription = "mitsa logo",
//                modifier= Modifier
//                    .fillMaxSize()
//            )
        }
        Card(
            elevation = 0.dp
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(all = 10.dp)
            )
            {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = pickedDate.toString(),
                        onValueChange = {},
                        leadingIcon = {
                            IconButton(onClick = {
                                dateDialogState.show()
                            }){
                                Icon(
                                    Icons.Default.DateRange,
                                    contentDescription="",
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                            }
                        },
                        label = {
                            Text(
                                text = "Select Date",
                                fontSize = 15.sp,
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)

                    )
                }
                Divider(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Complaint Details",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
                OutlinedTextField(
                    value = homeUiState?.title ?: "",
                    onValueChange = { homeViewModel?.onTitleChange(it) },
                    placeholder = {
                        Text(
                            text = "",
                            fontSize = 15.sp,
                        )
                    },
                    label = {
                        Text(
                            text = "Complaint Title",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                ExpandedDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),

                    listOfItems = listOf("General", "Electrical", "Plumbing", "Civil-Related", "System-Related"),
                    enable = true,
                    parentTextFieldCornerRadius = 5.dp,
                    placeholder = "Complaint Type",
                    dropdownItem = {name ->
                        Text(text = name)
                    },
                    onDropDownItemSelected = { item -> // Returns the item selected in the dropdown
                        homeViewModel?.onComplaintTypeChange(item)
                    },
                )


                OutlinedTextField(
                    value = homeUiState?.Description ?: "",
                    onValueChange = { homeViewModel?.onDescriptionChange(it) },
                    placeholder = {
                        Text(
                            text = "Description",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 5.dp)
                )
                Divider(
                    modifier = Modifier
                        .padding(all = 5.dp)
                )




                Button(
                    onClick = {
                        homeViewModel?.sendComplaint(context)
                        c += 1
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .fillMaxWidth()
                        .height(42.dp)
                )
                {
                    Text(text = "Submit")

                }
                if (c==69 || c==70) {
                    Text(text ="Never\ngonna\ngive\nyou\nup",
                        modifier = Modifier
                            .clickable {
                                c=0
                            }
                    )
                }
            }
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date"
        ) {
            if(it > LocalDate.now()) {
                Toast.makeText(context, "Action not allowed", Toast.LENGTH_SHORT).show()
            } else {
                homeViewModel?.onDateChange(it)
                pickedDate = it
            }
        }
    }
}


