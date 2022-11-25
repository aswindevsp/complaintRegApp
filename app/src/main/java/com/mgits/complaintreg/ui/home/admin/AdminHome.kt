package com.mgits.complaintreg.ui.home.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mgits.complaintreg.data.Complaints
import com.mgits.complaintreg.data.DataOrException
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun AdminHome(dataOrException: DataOrException<List<Complaints>, Exception>) {
    val products = dataOrException.data
    products?.let {
        LazyColumn {
            items(
                items = products
            ) { complaints ->
                ComplaintCard(complaints = complaints)
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