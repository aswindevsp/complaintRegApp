package com.mits.cms.repository



import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mits.cms.data.Complaints
import com.mits.cms.data.DataOrException
import com.mits.cms.data.UserDetails
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepository @Inject constructor(

) {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private var userId = auth.currentUser?.uid

    //for instance caching
    private var localUserDetails: DataOrException<UserDetails, Exception>? = null

    private suspend fun getAdminType(): String? {
        //updates user id if the userid is not fetched initially
        userId = Firebase.auth.currentUser!!.uid
        return db.collection("users").document(userId.toString())
            .get()
            .await()
            .getString("adminType")
    }

    suspend fun getComplaintsFromSever(): DataOrException<List<Complaints>, Exception> {
        Log.d(TAG, "reg complaints normal")
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
                    db.collection("complaints").orderBy("registeredTimeStamp", Query.Direction.DESCENDING).get()
                        .await().map {
                            it.toObject(Complaints::class.java)
                        }
            } catch (e: FirebaseFirestoreException) {
                dataOrException.e = e
            }
        }
        dataOrException.data
        return dataOrException
    }

    suspend fun getRegisteredComplaints(): DataOrException<List<Complaints>, Exception> {
        Log.d(TAG, "reg complaints")
        userId = Firebase.auth.currentUser!!.uid
        val dataOrException = DataOrException<List<Complaints>, Exception>()

        try {
            dataOrException.data =
                    db.collection("complaints").whereEqualTo("complainantID", userId).orderBy("registeredTimeStamp", Query.Direction.DESCENDING).get()
                        .await().map {
                            it.toObject(Complaints::class.java)
                        }
        } catch (e: FirebaseFirestoreException) {
                dataOrException.e = e
        }

        return dataOrException
    }


    suspend fun getUserDetails():DataOrException<UserDetails, Exception> {

        userId = Firebase.auth.currentUser?.uid
        var userDetails = DataOrException<UserDetails, Exception>()
        if(localUserDetails != null) {
            userDetails = localUserDetails as DataOrException<UserDetails, Exception>
        } else {
            if (userId != null) {
                try {
                    userDetails.data = db.collection("users").document(userId!!).get().await()
                        .toObject(UserDetails::class.java)
                } catch (_: FirebaseFirestoreException) {
                }
            }
        }

        localUserDetails = userDetails
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

    suspend fun getNewID(): Int {
        var prvID: Int = -2
        val db = Firebase.firestore

        db.collection("complaints").orderBy("complaintId", Query.Direction.DESCENDING).limit(1)
            .get()
            .addOnSuccessListener { dt ->
                if(!dt.isEmpty) {
                    val document = dt.documents[0]
                    prvID = document.getString("complaintId")!!.toInt()
                } else {
                    prvID = 0
                }
            }.await()

        return (prvID + 1)
    }


    suspend fun updateStatus(
        status: String,
        resolvedBy: String,
        cmpId: String
    ) {
        db.collection("complaints")
            .document(cmpId)
            .update(
                "status", status,
                "resolvedBy", resolvedBy,
                "resolvedByID", Firebase.auth.uid,
                "resolvedTimeStamp", Timestamp.now()
            )
    }


    suspend fun getLatestVersion(): Pair<String, String?> {
        var version = "-1"
        var link: String? = null
        return try {
            val documentSnapshot = db.collection("app").document("versions").get().await()
            version = documentSnapshot.getString("latestVersion") ?: "-1"
            link = documentSnapshot.getString("latestLink") ?: ""
            version to link
        } catch (_e: Exception) {
            version to link
        }
    }


}