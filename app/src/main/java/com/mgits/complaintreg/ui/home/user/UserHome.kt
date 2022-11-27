package com.mgits.complaintreg.ui.home


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UserHome(
    homeViewModel: UserHomeViewModel? = null,
    navController: NavController
){

    val homeUiState = homeViewModel?.homeUiState

    val context = LocalContext.current


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        modifier = Modifier
            .padding(all = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp
                )){
//            Image(
//                painter = painterResource(id = R.drawable.mila),
//                contentDescription = "mitsa logo",
//                modifier= Modifier
//                    .fillMaxSize()
//            )
        }
        Card(
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier=Modifier
                    .padding(all=10.dp)
            )
            {
                Text(text = "Enter Date of complaint")
                TextField(
                    value = "Working on it",
                    onValueChange = {  },
                    placeholder = {
                        Text(
                            text = "Date",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                Divider(
                    modifier=Modifier
                        .padding(vertical=5.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(text = "Title ")
                TextField(
                    value = homeUiState?.title?: "",
                    onValueChange = { homeViewModel?.onTitleChange(it)},
                    placeholder = {
                        Text(
                            text = "",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                Text(text = "Complaint Type ")
                TextField(
                    value = homeUiState?.complaintType ?: "",
                    onValueChange = { homeViewModel?.onComplaintTypeChange(it) },
                    placeholder = {
                        Text(
                            text = "",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                Divider(
                    modifier = Modifier
                        .padding(all=5.dp)
                )

                TextField(
                    value = homeUiState?.Description ?: "",
                    onValueChange = { homeViewModel?.onDescriptionChange(it)},
                    placeholder = {
                        Text(
                            text = "Description",
                            fontSize = 15.sp,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 5.dp)
                )
                Divider(
                    modifier = Modifier
                        .padding(all=5.dp)
                )


            }
        }
        Button(
            onClick = {
                homeViewModel?.sendComplaint(context)
            },
            shape = RoundedCornerShape(32.dp),
            modifier=Modifier
                .padding(all=32.dp)
        )
        {
            Text(text = "Submit")

        }
    }
}


