package com.mits.cms.ui.home.user


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mits.cms.navigation.ROUTE_USER_COMPLAINT_DETAILS


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserComplaintHistory(viewModel: UserHomeViewModel, navController: NavController) {

    LaunchedEffect(true) {
        viewModel.getComplaints()
    }
    if(viewModel.data.value.data?.isEmpty() == true) {
        Column(
            Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No complaints!", color = MaterialTheme.colorScheme.onSurface)
            Text(text = "Get started by registering a complaint.", color = MaterialTheme.colorScheme.onSurface)
        }
    } else {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)) {
            LazyColumn {
                items(
                    items = viewModel.data.value.data ?: listOf()
                ) { complaints ->
                    Card(
                        onClick = {
                            viewModel.updateCmpId(complaints)
                            navController.navigate(ROUTE_USER_COMPLAINT_DETAILS)
                        },
                        shape = MaterialTheme.shapes.small,

                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                top = 4.dp,
                                bottom = 4.dp
                            )
                            .fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(all = 12.dp)
                        ) {
                            complaints.complainant?.let { title ->
                                Text(
                                    text = title,
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .wrapContentWidth(Alignment.Start),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 25.sp
                                )
                            }
                            complaints.description?.let { description ->
                                Text(
                                    text = description,
                                    modifier = Modifier
                                        .wrapContentWidth(Alignment.End),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}