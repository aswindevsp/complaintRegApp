package com.mgits.cms.ui.home.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

    viewModel.getUnresolvedCount()
    viewModel.getComplaints()
    viewModel.getResolvedCount()

    var sortedState by remember {
        mutableStateOf(viewModel.sortState)
    }

    var popupControl by remember { mutableStateOf(false) }

    val complaints = dataOrException.data


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                        onClick = { popupControl = !popupControl },
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "MoreVert",
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
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .width(100.dp)
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .size(12.dp)
                                )
                                Box(
                                    modifier = Modifier.fillMaxWidth()
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

