package com.mgits.complaintreg.repository



import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.complaintreg.data.Complaints

import com.mgits.complaintreg.data.DataOrException

import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepository @Inject constructor(
    private val queryProductsByName: Query
) {
    suspend fun getComplaintsFromSever(): DataOrException<List<Complaints>, Exception> {
        val userId = Firebase.auth.currentUser?.uid
        val adminType = Firebase.firestore.collection("users").document(userId.toString())
            .get()
            .await()
            .getString("adminType")
        val dataOrException = DataOrException<List<Complaints>, Exception>()
        try {
            dataOrException.data = Firebase.firestore.collection("complaints").whereEqualTo("complaintType", adminType).get().await().map {
                it.toObject(Complaints::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }

        return dataOrException
    }

}