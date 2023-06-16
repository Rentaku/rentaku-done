package com.example.rentakucapstone.kendaraan

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class TambahKendaraanViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference

    fun saveDataMotor(userId: String, dataMotor: HashMap<String, Any>, file: File): Task<DocumentReference> {

        val folderName = "vehicle_img"
        val imageRef = storageRef.child("$folderName/${UUID.randomUUID()}")
        val uploadTask = imageRef.putFile(Uri.fromFile(file))

        return uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }
            imageRef.downloadUrl
        }.continueWithTask { task ->
            if (task.isSuccessful) {
                val imageUrl = task.result.toString()
                dataMotor["imageUrl"] = imageUrl
            } else {
                throw task.exception!!
            }
            firestore.collection("vehicles").add(dataMotor)
        }
    }

    fun getUserDocumentByEmail(email: String): Task<QuerySnapshot> {
        return firestore.collection("users")
            .whereEqualTo("email", email)
            .limit(1)
            .get()
    }
}