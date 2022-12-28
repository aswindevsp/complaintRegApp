package com.mgits.cms.ui.home.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mgits.cms.data.Complaints


@Composable
fun ComplaintCard(
    complaints: Complaints, navController: NavController, viewModel: AdminHomeViewModel
) {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
//        elevation = 3.dp,
//        onClick = {
//            complaints.complaintId?.let { viewModel.updateCmpId(complaints) }
//            navController.navigate("detailed-view")
//        }
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
                    color = Color.DarkGray,
                    fontSize = 25.sp
                )
            }
            complaints.description?.let { description ->
                Text(
                    text = description,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                    ,
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}