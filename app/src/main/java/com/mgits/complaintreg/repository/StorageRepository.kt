package com.mgits.complaintreg.repository



import android.service.controls.ControlsProviderService
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.data.Complaints

import com.mgits.complaintreg.data.DataOrException
import com.mgits.complaintreg.data.UserDetails
import kotlinx.coroutines.runBlocking

import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepository @Inject constructor(
    private val queryProductsByName: Query
) {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val userId = auth.currentUser?.uid

    private suspend fun getAdminType(): String? {

        return db.collection("users").document(userId.toString())
            .get()
            .await()
            .getString("adminType")
    }

    suspend fun getComplaintsFromSever(): DataOrException<List<Complaints>, Exception> {

        val adminType = getAdminType()
        val dataOrException = DataOrException<List<Complaints>, Exception>()
        try {
            dataOrException.data = db.collection("complaints").whereEqualTo("complaintType", adminType).get().await().map {
                it.toObject(Complaints::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }

        return dataOrException
    }

    suspend fun getUserDetails(): DataOrException<UserDetails, Exception> {
        val dataOrException = DataOrException<UserDetails, Exception>()
        try{
            dataOrException.data = userId?.let { db.collection("users").document(it).get().await().toObject(UserDetails::class.java) }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }


    suspend fun getComplaintCount():String {
        val adminType = getAdminType()
        val collection = db.collection("complaints")

        val pendingCount = mutableStateOf("")
        var resolvedCount: Long = 0;
        runBlocking {
            var query = collection.whereEqualTo("complaintType", adminType)
                .whereEqualTo("status", "resolved")
            var countQuery = query.count()
//        countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val snapshot = task.result
//                resolvedCount = snapshot.count
//                Log.d(TAG, "Count: ${snapshot.count}")
//            } else {
//                Log.d(TAG, "Count failed: ", task.exception)
//            }
//        }.await()

            query = collection.whereEqualTo("complaintType", adminType)
                .whereEqualTo("status", "unresolved")
            countQuery = query.count()
            countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result
                    pendingCount.value = snapshot.count.toString()
                    Log.d(TAG, "asdff 1: ${snapshot.count}")
                } else {
                    Log.d(TAG, "Count failed: ", task.exception)
                }
            }.await()
            Log.d(TAG, "asdff " + pendingCount.value)
        }
        return pendingCount.value
    }

}