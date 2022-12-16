package com.mgits.cms.ui.home.admin

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import com.mgits.cms.ui.home.user.UserDrawer



@OptIn(ExperimentalMaterialApi::class)
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
    val swipeRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = viewModel::getComplaints)

    val complaints = dataOrException.data
    if (complaints != null) {
        Log.d(TAG, "asdfasdf " + complaints.size.toString())
    }

    val materialBlue700= MaterialTheme.colors.background
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))


    Scaffold(
        scaffoldState = scaffoldState,
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
                    IconButton(onClick = {popupControl = !popupControl}) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "sort")
                    }
                }
            },backgroundColor = materialBlue700)  },
        drawerContent = { UserDrawer(navController = navController) }
    ) {contentPadding ->
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
                    .pullRefresh(swipeRefreshState)
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
                    PullRefreshIndicator(
                        refreshing = isLoading,
                        state = swipeRefreshState,
                        Modifier.align(Alignment.TopCenter)
                    )
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
                        elevation = 16.dp,
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




    val e = dataOrException.e
    e?.let {
        Text(
            text = e.message!!,
            modifier = Modifier.padding(16.dp)
        )
    }

}
