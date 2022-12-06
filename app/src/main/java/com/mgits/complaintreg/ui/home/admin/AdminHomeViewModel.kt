package com.mgits.complaintreg.ui.home.admin

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.mgits.complaintreg.data.Complaints
import com.mgits.complaintreg.data.DataOrException
import com.mgits.complaintreg.data.UserDetails
import com.mgits.complaintreg.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(
    private  val repository: StorageRepository
): ViewModel() {

    private val _isDataLoaded = MutableStateFlow(false)
    var isDataLoaded : StateFlow<Boolean> = _isDataLoaded



    private val _isLoading = MutableStateFlow(false)
    var isLoading = _isLoading.asStateFlow()

    var cmpId: String = ""

    var tempCompDetails: Complaints = Complaints("", "", "", "", "", "", "", )

    fun updateCmpId(id: Complaints) {
        tempCompDetails = id
    }

    val data: MutableState<DataOrException<List<Complaints>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    val userDetails: MutableState<DataOrException<UserDetails, Exception>> = mutableStateOf(
        DataOrException()
    )

    val num = mutableStateOf("")

    init {
        getComplaints()
        getUserDetails()
    }

    fun getComplaints() {
        viewModelScope.launch {
            _isLoading.value = true
            data.value = repository.getComplaintsFromSever()
            delay(1000)
            _isLoading.value = false
        }
    }


    fun getUserDetails() {
        viewModelScope.launch{
            userDetails.value = repository.getUserDetails()
            _isDataLoaded.value = true
        }

    }








}
