package com.mgits.cms.ui.home.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.mgits.cms.data.Complaints
import com.mgits.cms.data.DataOrException
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHome(
    dataOrException: DataOrException<List<Complaints>, Exception>,
    navController: NavController,
    viewModel: AdminHomeViewModel
) {

    val isLoading by viewModel.isLoading.collectAsState()
    var sortedState by remember {
        mutableStateOf(viewModel.sortState)
    }

    var popupControl by remember { mutableStateOf(false) }
    //val swipeRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = viewModel::getComplaints)

    val complaints = dataOrException.data

    val materialBlue700= MaterialTheme.colorScheme.background
    //val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }



    Scaffold(
        topBar = {
            TopAppBar(title = {
                              Text(text = "Pending: " + viewModel.unresolvedCount.value)
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = 8.dp
                        )
                ) {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "sort")
                    }
                }
            }) },
    ) {contentPadding ->
        Column(
            Modifier.padding(contentPadding)
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(12.dp))
                        items.forEach { item ->
                            NavigationDrawerItem(
                                icon = { Icon(item, contentDescription = null) },
                                label = { Text(item.name) },
                                selected = item == selectedItem.value,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    selectedItem.value = item
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {



                        Column() {
                            complaints?.let {
                                var compCopy = complaints
                                if(sortedState == "unresolved") {
                                    compCopy = compCopy.filter {
                                        it.status == "unresolved"
                                    }
                                }else if(sortedState == "resolved") {
                                    compCopy = compCopy.filter {
                                        it.status == "resolved"
                                    }
                                }

                                Box(modifier = Modifier
//                                    .pullRefresh(swipeRefreshState)
                                    .padding(contentPadding)
                                    .fillMaxSize()
                                ) {
                                    LazyColumn {
                                        items(
                                            items = compCopy
                                        ) { complaints ->
                                            ComplaintCard(complaints = complaints, navController, viewModel)
                                        }
                                    }
//                                    PullRefreshIndicator(
//                                        refreshing = isLoading,
//                                        state = swipeRefreshState,
//                                        Modifier.align(Alignment.TopCenter)
//                                    )
                                }
                            }

                            if (popupControl) {

                                Popup(
                                    alignment = Alignment.TopEnd,
                                    properties = PopupProperties(
                                        dismissOnBackPress = true,
                                        dismissOnClickOutside = true
                                    )

                                ) {
                                    Card(
                                        elevation = CardDefaults.cardElevation(0.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier
                                            .width(160.dp)
                                    ) {
                                        Column(
                                            horizontalAlignment= Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(4.dp),
                                            modifier = Modifier
                                                .width(100.dp)
                                        ) {
                                            Spacer(
                                                modifier = Modifier
                                                    .size(12.dp)
                                            )
                                            Box(modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(text = "   All", Modifier.clickable {
                                                    viewModel.sortState = "default"
                                                    sortedState = "default"
                                                    FontWeight.SemiBold
                                                })
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .size(12.dp)
                                            )
                                            Divider()
                                            Spacer(
                                                modifier = Modifier
                                                    .size(12.dp)
                                            )
                                            Box(modifier = Modifier.fillMaxWidth()) {
                                                Text(text = "   Unresolved", Modifier.clickable {
                                                    viewModel.sortState = "unresolved"
                                                    sortedState = "unresolved"
                                                    FontWeight.SemiBold
                                                })
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .size(12.dp)
                                            )
                                            Divider()
                                            Spacer(
                                                modifier = Modifier
                                                    .size(12.dp)
                                            )
                                            Box(modifier = Modifier.fillMaxWidth()) {
                                                Text(text = "   Resolved", Modifier.clickable {
                                                    viewModel.sortState = "resolved"
                                                    sortedState = "resolved"
                                                    FontWeight.SemiBold
                                                })
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .size(12.dp)
                                            )
                                            Divider()

                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            )
        }
    }




    val e = dataOrException.e
    e?.let {
        Text(
            text = e.message!!,
            modifier = Modifier.padding(16.dp)
        )
    }

}


