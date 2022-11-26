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
    dataOrException: DataOrException<List<Complaints>, Exception>,
    viewModel: AdminHomeViewModel
) {
    
    val complaints = dataOrException.data
    
    Text(text = viewModel.cmpId)

}
