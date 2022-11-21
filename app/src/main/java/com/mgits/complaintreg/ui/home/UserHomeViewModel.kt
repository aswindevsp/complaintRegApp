package com.mgits.complaintreg.ui.home

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserHomeViewModel(): ViewModel() {




    var homeUiState by mutableStateOf(HomeUiState())
        private set

    fun onTitleChange(title: String) {
        homeUiState = homeUiState.copy(title = title)
    }

    fun onComplaintTypeChange(complaintType: String) {
        homeUiState = homeUiState.copy(complaintType = complaintType)
    }

    fun onDescriptionChange(Description: String) {
        homeUiState = homeUiState.copy(Description = Description)
    }


    fun sendComplaint(context: Context) {
        val db = Firebase.firestore
        val uId = Firebase.auth.currentUser?.uid

        val payload = hashMapOf(
            "userId" to uId,
            "title" to homeUiState.title,
            "complaintType" to homeUiState.complaintType,
            "description" to homeUiState.Description
        )


        db.collection("complaints").document()
            .set(payload)
            .addOnSuccessListener {
                Toast.makeText(context, "It worked? Wah", Toast.LENGTH_LONG).show()
                onTitleChange("")
                onComplaintTypeChange("")
                onDescriptionChange("")
            }
            .addOnFailureListener {(Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show())}
    }



}



data class HomeUiState(
    val title: String = "",
    val name: String = "",
    val complaintType: String = "",
    val Description: String = "",
    val count: Int = 0
)