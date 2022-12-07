package com.mgits.complaintreg.ui.home.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mgits.complaintreg.data.Complaints
import com.mgits.complaintreg.data.DataOrException
import com.mgits.complaintreg.ui.auth.login.LoadingAnimation


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminHome(
    dataOrException: DataOrException<List<Complaints>, Exception>,
    navController: NavController,
    viewModel: AdminHomeViewModel
) {

    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = viewModel::getComplaints)

    val complaints = dataOrException.data

    if(dataOrException.data == null){
        viewModel.getComplaints()
    }

    complaints?.let {
        Box(Modifier.pullRefresh(swipeRefreshState)) {
            LazyColumn {
                items(
                    items = complaints
                ) { complaints ->
                    ComplaintCard(complaints = complaints, navController, viewModel)
                }
            }
            PullRefreshIndicator(refreshing = isLoading, state = swipeRefreshState, Modifier.align(Alignment.TopCenter))
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
