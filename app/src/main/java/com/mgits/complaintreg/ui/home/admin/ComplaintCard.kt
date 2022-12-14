package com.mgits.complaintreg.ui.home.admin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mgits.complaintreg.data.Complaints


@OptIn(ExperimentalMaterialApi::class)
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
        elevation = 3.dp,
        onClick = {
            complaints.complaintId?.let { viewModel.updateCmpId(complaints) }
            navController.navigate("detailed-view")
        }
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