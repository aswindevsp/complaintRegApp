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

class UserHomeViewModel(): ViewModel() {




    var homeUiState by mutableStateOf(HomeUiState())
        private set

    fun onLocationChange(location: String) {
        homeUiState = homeUiState.copy(location = location)
    }

    fun onFloorChange(floor: String) {
        homeUiState = homeUiState.copy(floor = floor)
    }

    fun onRoomChange(room: String) {
        homeUiState = homeUiState.copy(room = room)
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
        return if(homeUiState.floor.isBlank())
            false
        else if(homeUiState.room.isBlank())
            false
        else if(homeUiState.location.isBlank())
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
                    "location" to homeUiState.location,
                    "floor" to homeUiState.floor,
                    "room" to homeUiState.room,
                    "complaintType" to homeUiState.complaintType,
                    "description" to homeUiState.Description,
                    "timeStamp" to Timestamp.now(),
                    "date" to homeUiState.date.toString(),
                    "status" to "unresolved",
                    "resolvedBy" to ""
                )

                db.collection("complaints").document(complaintId)
                    .set(payload)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Complaint Registered", Toast.LENGTH_LONG).show()
                        onLocationChange("")
                        onFloorChange("")
                        onRoomChange("")
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
    val floor: String = "",
    val room: String = "",
    val name: String = "",
    val complaintType: String = "",
    val location: String = "",
    val block: String = "",
    val Description: String = "",
    val count: Int = 0,
    var date: LocalDate = LocalDate.now()
)