package com.mgits.cms.repository



import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mgits.cms.data.Complaints
import com.mgits.cms.data.DataOrException
import com.mgits.cms.data.UserDetails
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

        if(adminType != "Main") {
            try {
                dataOrException.data =
                    db.collection("complaints").whereEqualTo("complaintType", adminType).get()
                        .await().map {
                        it.toObject(Complaints::class.java)
                    }
            } catch (e: FirebaseFirestoreException) {
                dataOrException.e = e
            }
        } else {
            try {
                dataOrException.data =
                    db.collection("complaints").get()
                        .await().map {
                            it.toObject(Complaints::class.java)
                        }
            } catch (e: FirebaseFirestoreException) {
                dataOrException.e = e
            }
        }

        return dataOrException
    }

    suspend fun getUserDetails():DataOrException<UserDetails, Exception> {
        val userDetails = DataOrException<UserDetails, Exception>()
        try{
            userDetails.data = userId?.let { db.collection("users").document(it).get().await().toObject(UserDetails::class.java) }!!
        } catch (_: FirebaseFirestoreException) {

        }
        Log.d("TAG", "User details: $userDetails")
        return userDetails
    }


    suspend fun getUnResolvedCount(): String? {
        val adminType = getAdminType()
        var count: String = ""

        if(adminType != "Main") {
            try {
                count = db.collection("complaints")
                    .whereEqualTo("complaintType", adminType)
                    .whereEqualTo("status", "unresolved")
                    .count()
                    .get(AggregateSource.SERVER)
                    .await()
                    .count
                    .toString()
            } catch (_: Exception) {
            }
        } else {
            try {
                count = db.collection("complaints")
                    .whereEqualTo("status", "unresolved")
                    .count()
                    .get(AggregateSource.SERVER)
                    .await()
                    .count
                    .toString()
            } catch (_: Exception) {
            }
        }
        return count
    }

    suspend fun getResolvedCount(): String? {
        val adminType = getAdminType()
        var count: String = ""

        try{
            count = db.collection("complaints")
                .whereEqualTo("complaintType", adminType)
                .whereEqualTo("status", "resolved")
                .count()
                .get(AggregateSource.SERVER)
                .await()
                .count
                .toString()
        } catch (_: Exception) { }
        return count
    }



    suspend fun getStatus(cmpId: String): String? {
        var status: String =""
        val db = Firebase.firestore.collection("complaints").document(cmpId)

        try {
            status = db.get().await().getString("status").toString()
        } catch (_:Exception){

        }
        return status
    }


}