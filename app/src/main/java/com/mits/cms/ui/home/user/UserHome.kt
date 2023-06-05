package com.mits.cms.ui.home.user


import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.mits.cms.navigation.ROUTE_USER_PROFILE
import com.mits.cms.ui.auth.register.ExpandedDropDown
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserHome(
    viewModel: UserHomeViewModel,
    navController: NavController
) {

    val homeUiState = viewModel.homeUiState
    val context = LocalContext.current

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //Selected menu in drawer
    var selectedItem by remember { mutableStateOf(viewModel.pageState) }


        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(ROUTE_USER_PROFILE) },
                    ) {

                    }
                },
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = selectedItem,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
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
                verticalArrangement = Arrangement.Bottom
            ) {
                if(selectedItem == "Register") {
                    ComplaintForm(
                        viewModel = viewModel,
                        homeUiState = homeUiState,
                        context =context
                    )
                } else {
                    UserComplaintHistory(
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom) {
                    val items = listOf("Register", "History")
                    var selected by remember {
                        mutableStateOf(0)
                    }
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = {
                                    if(item == "History")
                                        Icon(Icons.Filled.History, contentDescription = item)
                                    else
                                        Icon(Icons.Filled.EditNote, contentDescription = item)
                                       },
                                label = { Text(item) },
                                selected = selected == index,
                                onClick = {
                                    selectedItem = item
                                    viewModel.pageState = item
                                    selected = index
                                          },
                            )
                        }
                    }
                }
            }

        }
    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplaintForm(
    viewModel: UserHomeViewModel,
    homeUiState: HomeUiState,
    context: Context
) {

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState = rememberMaterialDialogState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(all = 10.dp)
                .verticalScroll(rememberScrollState())
        )
        {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
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
                            text = "Date",
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
                text = "Complaint Details:",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
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
                    viewModel.onComplaintTypeChange(item)
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
                    viewModel.onLocationChange(item)
                },
            )

            if(homeUiState.location != "Others") {
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
                        viewModel.onFloorChange(item)
                    },
                )
            } else {
                OutlinedTextField(
                    value = homeUiState.otherLocation,
                    onValueChange = { viewModel.onOtherLocationChange(it) },
                    placeholder = {
                        Text(
                            text = "",
                            fontSize = 15.sp,
                        )
                    },
                    label = {
                        Text(
                            text = "Specify Location",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = homeUiState.room,
                onValueChange = { viewModel.onRoomChange(it) },
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
                value = homeUiState.Description,
                onValueChange = { viewModel.onDescriptionChange(it) },
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
                    viewModel.sendComplaint(context)
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
                viewModel.onDateChange(it)
                pickedDate = it
            }
        }
    }
}
