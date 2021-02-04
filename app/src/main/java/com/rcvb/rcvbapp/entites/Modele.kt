package com.rcvb.rcvbapp.entites

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirestoreCollections {

    companion object {
        val FIRESTORE_DATABASE = FirebaseFirestore.getInstance()
        val FIRESTORE_UTILS = Firebase.firestore.collection("utilisateurs")
        val FIRESTORE_ARTICLES = Firebase.firestore.collection("articles")

    }


}