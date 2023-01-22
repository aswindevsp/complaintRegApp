package com.mgits.cms.ui.home.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    if(viewModel.data.value.data!!.isEmpty()) {
        viewModel.getUnresolvedCount()
        viewModel.getComplaints()
        viewModel.getResolvedCount()
    }

    var sortedState by remember {
        mutableStateOf(viewModel.sortState)
    }

    var popupControl by remember { mutableStateOf(false) }

    val complaints = dataOrException.data


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column() {
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
                        IconButton(onClick = {scope.launch { drawerState.close() }}) {
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
                            text = "Complaints",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "Pending: " + viewModel.unresolvedCount.value,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
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
                        onClick = { expanded = !expanded},
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "MoreVert",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
            Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopEnd)) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All") },
                        onClick = {
                            viewModel.sortState = "default"
                            sortedState = "default" })
                    DropdownMenuItem(
                        text = { Text("Unresolved") },
                        onClick = {
                            viewModel.sortState = "unresolved"
                            sortedState = "unresolved"
                        })
                    DropdownMenuItem(
                        text = { Text("Resolved") },
                        onClick = {
                            viewModel.sortState = "resolved"
                            sortedState = "resolved" },
                        )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                complaints?.let {
                    var compCopy = complaints
                    if (sortedState == "unresolved") {
                        compCopy = compCopy.filter {
                            it.status == "unresolved"
                        }
                    } else if (sortedState == "resolved") {
                        compCopy = compCopy.filter {
                            it.status == "resolved"
                        }
                    }

                    LazyColumn {
                        items(
                            items = compCopy
                        ) { complaints ->
                            ComplaintCard(complaints = complaints, navController, viewModel)
                        }
                    }
                }
            }



        }
    }

}

