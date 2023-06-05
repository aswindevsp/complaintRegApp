package com.mits.cms.ui.home.admin

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mits.cms.data.Complaints
import com.mits.cms.data.DataOrException
import com.mits.cms.data.UserDetails
import com.mits.cms.repository.StorageRepository
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

     fun getResolvedCount() {
        viewModelScope.launch {
            resolvedCount.value = repository.getResolvedCount().toString()
        }
    }
     fun getUnresolvedCount() {
        viewModelScope.launch {
            unresolvedCount.value = repository.getUnResolvedCount().toString()
        }
    }
    fun getComplaints() {
        viewModelScope.launch {
            data.value = repository.getComplaintsFromSever()
            getUnresolvedCount()
            Log.d(TAG, data.value.toString())
            delay(1500)
            _isLoading.value = false
        }
    }

    fun updateStatus(status: String, resolvedBy: String) {
        val cmpId = tempCompDetails.complaintId?: ""

        viewModelScope.launch {
            repository.updateStatus(status, resolvedBy, cmpId)
        }
    }
}
