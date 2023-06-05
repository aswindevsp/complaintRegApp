package com.mits.cms.ui.loading

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mits.cms.navigation.ROUTE_ADMIN_HOME
import com.mits.cms.navigation.ROUTE_EMAIL_VERIFICATION
import com.mits.cms.navigation.ROUTE_LOGIN
import com.mits.cms.navigation.ROUTE_USER_HOME
import com.mits.cms.repository.AuthRepository
import com.mits.cms.repository.StorageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val storageRepo: StorageRepository = StorageRepository()
) : ViewModel() {

    suspend fun isAppUpToDate(context: Context, navController: NavController) {
        if (Firebase.auth.currentUser != null) {
            val (latestVersion, url) = storageRepo.getLatestVersion()

            val localVersion = try {
                val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                // Handle the error
                null
            }


            if (latestVersion != localVersion) {
                Toast.makeText(context, "Update Available", Toast.LENGTH_LONG).show()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } else {
                check(navController)
            }
        } else {
            check(navController)
        }
    }

    private val hasUser: Boolean
        get() = repository.hasUser()

    private var isAdminVal: Boolean? = null
    private fun isAdmin() {
        viewModelScope.launch {
            repository.isAdmin {
                isAdminVal = it
            }
            if (isAdminVal != null && isAdminVal == true)
                isAdminVal = true
        }

    }

    private var isEmailVerified: Boolean? = null
    private fun isEmailVerified() {
        isEmailVerified = repository.isEmailVerified()
    }

    fun check(navController: NavController) {
        if (hasUser) {
            isAdmin()
            isEmailVerified()
            val emailList = listOf<String>(
                "general@mgits.ac.in",
                "electrical@mgits.ac.in",
                "plumbing@mgits.ac.in",
                "civil@mgits.ac.in",
                "system@mgits.ac.in"
            )
            val emailVal = Firebase.auth.currentUser!!.email!!.lowercase()
            if (!Firebase.auth.currentUser!!.isEmailVerified && emailVal !in emailList) {
                viewModelScope.launch {
                    navController.navigate(ROUTE_EMAIL_VERIFICATION) {
                        popUpTo(0)
                    }
                }
            } else {
                viewModelScope.launch {
                    delay(800)
                    while (isAdminVal == null) {
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