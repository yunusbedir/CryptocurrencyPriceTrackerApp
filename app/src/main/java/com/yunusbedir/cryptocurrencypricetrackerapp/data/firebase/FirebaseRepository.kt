package com.yunusbedir.cryptocurrencypricetrackerapp.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yunusbedir.cryptocurrencypricetrackerapp.util.FirebaseConstants.COLLECTION_USERS
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    fun login(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun register(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun forgotPassword(email: String) =
        firebaseAuth.sendPasswordResetEmail(email)

    fun updatePassword(password: String) =
        firebaseAuth.currentUser?.updatePassword(password)

    fun saveUser() =
        firebaseAuth.currentUser?.let {
            firebaseFirestore.collection(COLLECTION_USERS).document(it.email.toString())
                .set(it)
        }

    private fun getUserCollection() =
        firebaseAuth.currentUser?.let {
            firebaseFirestore.collection(COLLECTION_USERS).document(it.email.toString())
        }

    fun getCurrentUser() =
        firebaseAuth.currentUser

}