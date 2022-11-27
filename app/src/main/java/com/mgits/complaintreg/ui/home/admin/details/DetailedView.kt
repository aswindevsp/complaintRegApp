package com.mgits.complaintreg.ui.home.admin.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.ServerTimestamp
import com.mgits.complaintreg.data.Complaints
import com.mgits.complaintreg.data.DataOrException
import com.mgits.complaintreg.ui.home.admin.AdminHomeViewModel
import java.util.*

@Composable
fun DetailedView(
    viewModel: AdminHomeViewModel
) {

    Column() {
        Text(text = viewModel.tempCompDetails.title.toString())
        Text(text = viewModel.tempCompDetails.description.toString())
        //Text(text = viewModel.tempCompDetails.complaintType.toString())
        Text(text = viewModel.tempCompDetails.description.toString())
        Text(text = viewModel.tempCompDetails.status.toString())
        Text(text = viewModel.tempCompDetails.name.toString())

    }

}
