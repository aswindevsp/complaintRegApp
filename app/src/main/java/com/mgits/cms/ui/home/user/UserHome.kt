package com.mgits.cms.ui.home.user


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mgits.cms.navigation.ROUTE_USER_PROFILE
import com.mgits.cms.ui.auth.register.ExpandedDropDown
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserHome(
    homeViewModel: UserHomeViewModel,
    navController: NavController
) {

    val homeUiState = homeViewModel.homeUiState

    val context = LocalContext.current

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState = rememberMaterialDialogState()
    var c by remember {
        mutableStateOf(0)
    }
//    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MITS CMS".uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = { scope.launch { drawerState.close() } }) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = "Close drawer"
                            )
                        }
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .fillMaxSize()
        ) {
            TopAppBar(
                modifier = Modifier,
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                ),
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Register Complaint",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(ROUTE_USER_PROFILE) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "profile",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
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
                                    }) {
                                        Icon(
                                            Icons.Default.DateRange,
                                            contentDescription = "",
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
                            fontSize = 12.sp,
                        )


                        ExpandedDropDown(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),

                            listOfItems = listOf(
                                "General",
                                "Electrical",
                                "Plumbing",
                                "Civil-Related",
                                "System-Related"
                            ),
                            parentTextFieldCornerRadius = 5.dp,
                            placeholder = "Complaint Type",
                            value = homeUiState.complaintType,
                            dropdownItem = { name ->
                                Text(text = name)
                            },
                            onDropDownItemSelected = { item -> // Returns the item selected in the dropdown
                                homeViewModel.onComplaintTypeChange(item)
                            },
                            readOnly = true
                        )

                        ExpandedDropDown(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),

                            listOfItems = listOf(
                                "M George Block",
                                "Ramanujan block",
                                "Canteen Block",
                                "Others"
                            ),
                            parentTextFieldCornerRadius = 5.dp,
                            value = homeUiState.location,
                            placeholder = "Location",
                            dropdownItem = { name ->
                                Text(text = name)
                            },
                            onDropDownItemSelected = { item -> // Returns the item selected in the dropdown
                                homeViewModel.onLocationChange(item)
                            },
                        )

                        ExpandedDropDown(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),

                            listOfItems = listOf(
                                "0", "1", "2", "3", "4", "5"
                            ),
                            parentTextFieldCornerRadius = 5.dp,
                            placeholder = "Floor",
                            value = homeUiState.floor,
                            dropdownItem = { name ->
                                Text(text = name)
                            },
                            onDropDownItemSelected = { item -> // Returns the item selected in the dropdown
                                homeViewModel.onFloorChange(item)
                            },
                        )

                        OutlinedTextField(
                            value = homeUiState.room,
                            onValueChange = { homeViewModel.onRoomChange(it) },
                            placeholder = {
                                Text(
                                    text = "",
                                    fontSize = 15.sp,
                                )
                            },
                            label = {
                                Text(
                                    text = "Room",
                                    fontSize = 15.sp,
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            singleLine = true
                        )


                        OutlinedTextField(
                            value = homeUiState.Description ,
                            onValueChange = { homeViewModel.onDescriptionChange(it) },
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
                                homeViewModel.sendComplaint(context)
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
                        if (c == 69 || c == 70) {
                            Text(text = "Never\ngonna\ngive\nyou\nup",
                                modifier = Modifier
                                    .clickable {
                                        c = 0
                                    }
                            )
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
                    if (it > LocalDate.now()) {
                        Toast.makeText(context, "Action not allowed", Toast.LENGTH_SHORT).show()
                    } else {
                        homeViewModel.onDateChange(it)
                        pickedDate = it
                    }
                }
            }
        }
    }
}
