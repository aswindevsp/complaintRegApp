package com.mgits.cms.ui.home.admin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.cms.data.Complaints
import com.mgits.cms.data.DataOrException
import com.mgits.cms.data.UserDetails
import com.mgits.cms.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private  val repository: StorageRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    var isLoading = _isLoading.asStateFlow()

    var sortState: String = "unresolved"
    var tempCompDetails: Complaints = Complaints("", "", "", "", "", "", "", )

    val data: MutableState<DataOrException<List<Complaints>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    var userDetails: MutableState<DataOrException<UserDetails, Exception>> = mutableStateOf(
        DataOrException()
    )

    val unresolvedCount: MutableState<String> =  mutableStateOf("")
    val resolvedCount: MutableState<String> =  mutableStateOf("")

    val status: MutableState<String?> = mutableStateOf(tempCompDetails.status)




    fun updateCmpId(id: Complaints) {
        tempCompDetails = id
    }


    init {
        getUnresolvedCount()
        getComplaints()
        //getUserDetails()
        getResolvedCount()

    }

    private fun getResolvedCount() {
        viewModelScope.launch {
            resolvedCount.value = repository.getResolvedCount().toString()
        }
    }
    private fun getUnresolvedCount() {
        viewModelScope.launch {
            unresolvedCount.value = repository.getUnResolvedCount().toString()
            _isLoading.value = false
        }
    }

    fun getComplaints() {
        viewModelScope.launch {
            _isLoading.value = true
            data.value = repository.getComplaintsFromSever()
            getUnresolvedCount()
            delay(1000)
            _isLoading.value = false
        }
    }

    fun updateStatus(status: String, resolvedBy: String) {
        val cmpId = tempCompDetails.complaintId?: ""
        Firebase.firestore
            .collection("complaints")
            .document(cmpId)
            .update(
                "status", status,
                "resolvedBy", resolvedBy,
                "resolvedByID", Firebase.auth.uid,
                "resolvedTimeStamp", Timestamp.now()
            )


    }


//
//    private fun getUserDetails() {
//        viewModelScope.launch{
//           userDetails.value = repository.getUserDetails()
//        }
//
//    }

}
