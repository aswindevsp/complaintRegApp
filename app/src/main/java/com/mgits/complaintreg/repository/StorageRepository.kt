package com.mgits.complaintreg.repository


import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
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
        val dataOrException = DataOrException<List<Complaints>, Exception>()
        try {
            dataOrException.data = queryProductsByName.get().await().map { document ->
                document.toObject(Complaints::class.java)
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }

}