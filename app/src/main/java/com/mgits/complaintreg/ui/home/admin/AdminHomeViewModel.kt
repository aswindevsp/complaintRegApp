package com.mgits.complaintreg.ui.home.admin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgits.complaintreg.data.Complaints
import com.mgits.complaintreg.data.DataOrException
import com.mgits.complaintreg.data.UserDetails
import com.mgits.complaintreg.repository.StorageRepository
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
            data.value = repository.getComplaintsFromSever()
            delay(1000)
        }
    }

//
//    private fun getUserDetails() {
//        viewModelScope.launch{
//           userDetails.value = repository.getUserDetails()
//        }
//
//    }

}
