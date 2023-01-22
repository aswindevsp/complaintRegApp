package com.mgits.cms.ui.home.user

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserDetailedView(
    navController: NavController,
    viewModel: UserHomeViewModel
) {

    var resolvedBy by remember {
        mutableStateOf("")
    }

    val openDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back arrow")
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = 10.dp
                        )
                ) {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                    }
                }
            }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background))
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())

        ) {

            Column(
                Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Registered Complaint: ",
                    fontSize = 24.sp,
//                style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = "Complaint ID",
                    modifier = Modifier.height(24.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
//                style = MaterialTheme.typography.caption
                )
                Text(
                    text = viewModel.tempCompDetails.complaintId.toString(),
                    fontSize = 18.sp,
//                style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Visible
                )

                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = "Location",
                    modifier = Modifier.height(24.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
//                style = MaterialTheme.typography.caption
                )
                Text(
                    text = viewModel.tempCompDetails.location.toString(),
                    fontSize = 18.sp,
//                style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Visible
                )

                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )

                Row() {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Floor",
                            modifier = Modifier.height(24.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
//                        style = MaterialTheme.typography.caption
                        )
                        Text(
                            text = viewModel.tempCompDetails.floor.toString(),
                            fontSize = 18.sp,
//                        style = MaterialTheme.typography.body1,
                            overflow = TextOverflow.Visible
                        )
                    }



                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Room",
                            modifier = Modifier.height(24.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
//                        style = MaterialTheme.typography.caption
                        )
                        Text(
                            text = viewModel.tempCompDetails.room.toString(),
                            fontSize = 18.sp,
//                        style = MaterialTheme.typography.body1,
                            overflow = TextOverflow.Visible
                        )
                    }
                }




                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = "Description",
                    modifier = Modifier.height(24.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
//                style = MaterialTheme.typography.caption
                )
                Text(
                    text = viewModel.tempCompDetails.description.toString(),
                    fontSize = 18.sp,
//                style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Visible
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = "Name",
                    modifier = Modifier.height(24.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
//                style = MaterialTheme.typography.caption
                )
                Text(
                    text = viewModel.tempCompDetails.complainant.toString(),
                    fontSize = 18.sp,
//                style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Visible
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )
//            Text(
//                text = "Issue Status",
//                modifier = Modifier.height(24.dp),
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                color = Color.Gray,
//                style = MaterialTheme.typography.caption
//            )
//            if (viewModel.tempCompDetails.status == "resolved") {
//                Icon(
//                    imageVector = Icons.Outlined.CheckCircle, contentDescription = "",
//                    modifier = Modifier
//                        .size(56.dp)
//                        .padding(
//                            vertical = 10.dp,
//                            horizontal = 0.dp
//                        )
//                )
//            } else {
//                Icon(
//                    imageVector = Icons.Outlined.Cancel, contentDescription = "",
//                    modifier = Modifier
//                        .size(56.dp)
//                        .padding(
//                            vertical = 10.dp,
//                            horizontal = 0.dp
//                        )
//                )
//
//            }
                if (viewModel.tempCompDetails.resolvedBy == null) {

                    Text(text = "Not yet resolved")
                } else {
                    Text(
                        text = "Resolved By",
                        modifier = Modifier.height(24.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
//                style = MaterialTheme.typography.caption
                    )
                    viewModel.tempCompDetails.resolvedBy?.let { it1 ->
                        OutlinedTextField(
                            value = it1,
                            onValueChange = {
                                resolvedBy = it;
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            readOnly = true
                        )
                    }
                }
            }

        }
    }

}

