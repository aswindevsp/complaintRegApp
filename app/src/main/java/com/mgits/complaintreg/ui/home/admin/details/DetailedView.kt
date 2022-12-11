package com.mgits.complaintreg.ui.home.admin.details

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.navigation.ROUTE_LOGIN
import com.mgits.complaintreg.ui.home.admin.AdminHomeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailedView(
    navController: NavController,
    viewModel: AdminHomeViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "",
                    modifier = Modifier
                        .padding(
                            vertical = 10.dp,
                            horizontal = 0.dp
                        )
                        .clickable {navController.popBackStack()}
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = 10.dp
                        )
                ) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "",
                        modifier= Modifier
                            .padding(
                                vertical = 10.dp,
                                horizontal = 0.dp,
                            )
                            .clickable {
                                Firebase.auth.signOut()
                                navController.navigate(ROUTE_LOGIN){
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                                }
                            }
                    )
                }
            },backgroundColor = MaterialTheme.colors.background) }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(8.dp)

        ) {


            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Registered Complaint: ",
                fontSize = 24.sp,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Title",
                modifier = Modifier.height(24.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = viewModel.tempCompDetails.title.toString(),
                fontSize = 18.sp,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Visible
            )

            Divider(
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Description",
                modifier = Modifier.height(24.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = viewModel.tempCompDetails.description.toString(),
                fontSize = 18.sp,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Visible
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Name",
                modifier = Modifier.height(24.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = viewModel.tempCompDetails.name.toString(),
                fontSize = 18.sp,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Visible
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Issue Status",
                modifier = Modifier.height(24.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                style = MaterialTheme.typography.caption
            )
            if (viewModel.tempCompDetails.status == "resolved") {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle, contentDescription = "",
                    modifier = Modifier
                        .size(56.dp)
                        .padding(
                            vertical = 10.dp,
                            horizontal = 0.dp
                        )
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Cancel, contentDescription = "",
                    modifier = Modifier
                        .size(56.dp)
                        .padding(
                            vertical = 10.dp,
                            horizontal = 0.dp
                        )
                )

            }
            Button(
                onClick = {
                    viewModel.updateStatus("resolved")
                    viewModel.getComplaints()
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(all = 12.dp)
                    .fillMaxWidth()
                    .height(42.dp)
            )
            {
                Text(text = "Mark as Resolved")
            }
        }
    }
}
