package com.mgits.complaintreg.ui.home.user

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


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
    fun onDateChange(date: LocalDate) {
        homeUiState = homeUiState.copy(date = date)
    }

    private fun checkForms(): Boolean {
        return if(homeUiState.title.isBlank())
            false
        else if(homeUiState.Description.isBlank())
            false
        else homeUiState.complaintType.isNotBlank()
    }

    fun sendComplaint(context: Context) {
        val db = Firebase.firestore
        val uId = Firebase.auth.currentUser?.uid

        if(!checkForms()) {
            Toast.makeText(context, "Some of the fields are left blank", Toast.LENGTH_LONG).show()
        } else {
            viewModelScope.launch {
                val name = uId?.let {
                    Firebase.firestore
                        .collection("users").document(it)
                        .get().await()
                        .getString("name")
                }

                val complaintId = db.collection("complaints").document().id

                val payload = hashMapOf(
                    "userId" to uId,
                    "complaintId" to complaintId,
                    "name" to name,
                    "title" to homeUiState.title,
                    "complaintType" to homeUiState.complaintType,
                    "description" to homeUiState.Description,
                    "timeStamp" to Timestamp.now(),
                    "date" to homeUiState.date.toString(),
                    "status" to "pending"
                )

                db.collection("complaints").document(complaintId)
                    .set(payload)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Complaint Registered", Toast.LENGTH_LONG).show()
                        onTitleChange("")
                        onComplaintTypeChange("")
                        onDescriptionChange("")
                    }
                    .addOnFailureListener {
                        (Toast.makeText(
                            context,
                            "Something went wrong",
                            Toast.LENGTH_LONG
                        ).show())
                    }
            }
        }

    }



}



data class HomeUiState(
    val title: String = "",
    val name: String = "",
    val complaintType: String = "",
    val Description: String = "",
    val count: Int = 0,
    var date: LocalDate = LocalDate.now()
)