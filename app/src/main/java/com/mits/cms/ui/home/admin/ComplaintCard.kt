package com.mits.cms.ui.home.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mits.cms.data.Complaints


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplaintCard(
    complaints: Complaints, navController: NavController, viewModel: AdminHomeViewModel
) {

    Card(
        onClick = {
            complaints.complaintId?.let { viewModel.updateCmpId(complaints) }
            navController.navigate("detailed-view")
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
        ){
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
                        .wrapContentWidth(Alignment.End)
                    ,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
            }
        }
    }
}