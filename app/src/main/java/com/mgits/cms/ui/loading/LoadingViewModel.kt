package com.mgits.cms.ui.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mgits.cms.navigation.ROUTE_ADMIN_HOME
import com.mgits.cms.navigation.ROUTE_EMAIL_VERIFICATION
import com.mgits.cms.navigation.ROUTE_LOGIN
import com.mgits.cms.navigation.ROUTE_USER_HOME
import com.mgits.cms.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {


    private val hasUser: Boolean
        get() = repository.hasUser()

    private var isAdminVal: Boolean? = null
    private fun isAdmin() {
        viewModelScope.launch {
            repository.isAdmin {
                isAdminVal = it
            }
            if(isAdminVal != null && isAdminVal == true)
               isAdminVal = true
        }

    }

    fun check(navController: NavController) {


        if (hasUser) {
            isAdmin()
            if(!Firebase.auth.currentUser!!.isEmailVerified) {
                navController.navigate(ROUTE_EMAIL_VERIFICATION) {
                    popUpTo(0)
                }
            } else {
                viewModelScope.launch {
                    delay(800)
                    while(isAdminVal == null) {
                        delay(800)
                    }
                    if (isAdminVal!!) {
                        navController.navigate(ROUTE_ADMIN_HOME) {
                            popUpTo(0)
                        }
                    } else {
                        navController.navigate(ROUTE_USER_HOME) {
                            popUpTo(0)
                        }
                    }
                }
            }
        } else {
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(0)
                }
        }
    }

}