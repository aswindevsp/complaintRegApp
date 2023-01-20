package com.mgits.cms.ui.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mgits.cms.navigation.ROUTE_ADMIN_HOME
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

    private var isAdminVal: Boolean = false
    private fun isAdmin() {
        viewModelScope.launch {
            repository.isAdmin {
                isAdminVal = it
            }
            if(isAdminVal)
               isAdminVal = true
        }

    }

    fun check(navController: NavController) {
        if (hasUser) {
            viewModelScope.launch {
                delay(800)
                isAdmin()
                if (isAdminVal) {
                    navController.navigate(ROUTE_ADMIN_HOME) {
                        popUpTo(0)
                    }
                } else {
                    navController.navigate(ROUTE_USER_HOME) {
                        popUpTo(0)
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