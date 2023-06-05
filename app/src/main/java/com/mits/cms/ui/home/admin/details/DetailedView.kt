package com.mits.cms.ui.home.admin.details

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mits.cms.ui.home.admin.AdminHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedView(
    navController: NavController,
    viewModel: AdminHomeViewModel
) {

    var resolvedBy by remember {
        mutableStateOf("")
    }

    val openDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val intent = remember {Intent(Intent.ACTION_DIAL, Uri.parse("tel:${viewModel.tempCompDetails.contact}"))}

    Scaffold(
        topBar = {
            TopAppBar(title = {
                IconButton(onClick = {navController.popBackStack() }) {
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
            }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)) }
    ) {contentPadding ->
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
                Text(
                    text = "Registered Complaint: ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = "Complaint ID",
                    modifier = Modifier.height(24.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = viewModel.tempCompDetails.complaintId.toString(),
                    fontSize = 18.sp,
                    overflow = TextOverflow.Visible,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )

                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Location",
                            modifier = Modifier.height(24.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = viewModel.tempCompDetails.location.toString(),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Visible,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }



                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Type",
                            modifier = Modifier.height(24.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = viewModel.tempCompDetails.complaintType.toString(),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Visible,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }


                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )

                Row {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Floor",
                            modifier = Modifier.height(24.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = viewModel.tempCompDetails.floor.toString(),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Visible,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }



                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Room",
                            modifier = Modifier.height(24.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = viewModel.tempCompDetails.room.toString(),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Visible,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = viewModel.tempCompDetails.description.toString(),
                    fontSize = 18.sp,
                    overflow = TextOverflow.Visible,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = viewModel.tempCompDetails.complainant.toString(),
                    fontSize = 18.sp,
                    overflow = TextOverflow.Visible,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column() {
                        Text(
                            text = "Phone No",
                            modifier = Modifier
                                .height(24.dp),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = viewModel.tempCompDetails.contact.toString(),
                            fontSize = 18.sp,
                            overflow = TextOverflow.Visible,
                            color = MaterialTheme.colorScheme.onSurfaceVariant

                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = { context.startActivity(intent) }) {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = "Call", tint = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                }
                Divider(
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = "Resolved By",
                    modifier = Modifier.height(24.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )


                if(viewModel.tempCompDetails.resolvedBy == null) {
                    OutlinedTextField(
                        value = resolvedBy,
                        onValueChange = {
                            resolvedBy = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    Button(
                        onClick = {
                            if (resolvedBy.isNotBlank()) {
                                openDialog.value = true
                            } else {
                                Toast.makeText(context, "Resolved By field empty", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .padding(all = 12.dp)
                            .fillMaxWidth()
                            .height(42.dp)
                    )
                    {
                        Text(text = "Mark as Resolved")
                    }
                } else {
                    viewModel.tempCompDetails.resolvedBy?.let { it1 ->
                        OutlinedTextField(
                            value = it1,
                            onValueChange = {
                                resolvedBy = it
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

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Confirm Action")
            },
            text = {
                Text(
                    "Are you sure you want to mark this complaint as resolved? "
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        viewModel.updateStatus("resolved", resolvedBy)
                        viewModel.getComplaints()
                        navController.popBackStack()
                        Toast.makeText(
                            context,
                            "Complaint marked as resolved",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

