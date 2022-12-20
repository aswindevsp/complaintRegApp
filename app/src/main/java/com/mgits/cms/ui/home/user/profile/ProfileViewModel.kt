package com.mgits.cms.ui.home.user.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mgits.cms.data.DataOrException
import com.mgits.cms.data.UserDetails
import com.mgits.cms.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private  val repository: StorageRepository
) : ViewModel() {


    var userDetails: MutableState<DataOrException<UserDetails, Exception>> = mutableStateOf(
        DataOrException()
    )


    fun getUserDetails() {
        viewModelScope.launch {
            userDetails.value = repository.getUserDetails()
        }
    }


    fun signOut() {
        Firebase.auth.signOut()
    }


}
