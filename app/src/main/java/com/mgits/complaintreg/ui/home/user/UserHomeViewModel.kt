package com.mgits.complaintreg.ui.home

import android.content.Context
import android.service.controls.ControlsProviderService
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.grpc.util.GracefulSwitchLoadBalancer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
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

    private fun getName(uId: String){

    }

    fun sendComplaint(context: Context) {
        val db = Firebase.firestore
        val uId = Firebase.auth.currentUser?.uid


        GlobalScope.launch(Dispatchers.IO) {
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
                "status" to "pending"
            )

            db.collection("complaints").document(complaintId)
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



}



data class HomeUiState(
    val title: String = "",
    val name: String = "",
    val complaintType: String = "",
    val Description: String = "",
    val count: Int = 0
)