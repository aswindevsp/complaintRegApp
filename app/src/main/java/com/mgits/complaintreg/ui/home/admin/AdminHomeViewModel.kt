package com.mgits.complaintreg.ui.home.admin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.mgits.complaintreg.data.Complaints
import com.mgits.complaintreg.data.DataOrException
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

    init {
        getComplaints()
    }

    fun getComplaints() {
        viewModelScope.launch {
            _isLoading.value = true
            data.value = repository.getComplaintsFromSever()
            delay(1000)
            _isLoading.value = false
        }
    }








}
