package com.mgits.cms.repository



import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {


    fun hasUser():Boolean = Firebase.auth.currentUser != null


    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()


    suspend fun createUser(
        email:String,
        password:String,
        onComplete:(Boolean) ->Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onComplete.invoke(true)
                }else{
                    onComplete.invoke(false)
                }
            }.await()
    }
    suspend fun login(
        email:String,
        password:String,
        onComplete:(Boolean) ->Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onComplete.invoke(true)
                }else{
                    onComplete.invoke(false)
                }
            }.await()
    }

    suspend fun resetPassword(
        email:String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth
            .sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onComplete.invoke(true)
                }else{
                    onComplete.invoke(false)
                }
            }.await()
    }

     suspend fun isAdmin(
        onComplete: (Boolean) -> Unit
    ) {
        val db = Firebase.firestore
        db.collection("users").document(getUserId()).get()
            .addOnSuccessListener { document ->
                if (document.getBoolean("admin") == true) {
                   onComplete.invoke(true)
                }else {
                    onComplete.invoke(false)
                }
            }.await()


    }


}