package com.cpimca.scrapwala
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RegisterViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val storageRef = Firebase.storage.reference
    private val workersData = MutableLiveData<List<Worker>>()

    fun registerWorker(worker: Worker) {
        firestore.collection("workers")
            .add(worker)
            .addOnSuccessListener { documentReference ->
                // Handle success
            }
            .addOnFailureListener { e ->
                // Handle errors
            }
    }

    fun uploadImageAndGetDownloadUrl(imageUri: Uri, onSuccess: (String) -> Unit) {
        val imageRef = storageRef.child("images/${imageUri.lastPathSegment}")
        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl
                    .addOnSuccessListener { uri ->
                        onSuccess(uri.toString())
                    }
            }
            .addOnFailureListener { e ->
                // Handle errors
            }
    }

    fun createWorker(
        name: String,
        phone: String,
        email: String,
        address: String,
        imageUri: Uri
    ) {
        uploadImageAndGetDownloadUrl(imageUri) { imageUrl ->
            val worker = Worker(name, phone, email, address, imageUrl)
            registerWorker(worker)
        }
    }


    fun getWorkersData(): LiveData<List<Worker>> {
        firestore.collection("workers")
            .get()
            .addOnSuccessListener { documents ->
                val workers = mutableListOf<Worker>()
                for (document in documents) {
                    val worker = document.toObject<Worker>()
                    workers.add(worker)
                }
                workersData.value = workers
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
        return workersData
    }

    fun deleteWorker(workerName: String, onDelete: (Boolean) -> Unit) {
        val firestore = Firebase.firestore
        val workersRef = firestore.collection("workers")

        // Query workers collection based on name
        workersRef.whereEqualTo("name", workerName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    // No matching worker found
                    onDelete(false) // Notify caller with failure status
                    return@addOnSuccessListener
                }

                // Delete each matching worker document
                val batch = firestore.batch()
                querySnapshot.documents.forEach { document ->
                    batch.delete(document.reference)
                }

                // Commit the batch deletion
                batch.commit()
                    .addOnSuccessListener {
                        // Ensure onDelete is called on the main thread
                        onDelete(true) // Notify caller with success status
                    }
                    .addOnFailureListener { e ->
                        // Ensure onDelete is called on the main thread
                        onDelete(false) // Notify caller with failure status
                        Log.e(ContentValues.TAG, "Error deleting worker: ", e)
                    }
            }
            .addOnFailureListener { e ->
                // Handle failure
                onDelete(false) // Notify caller with failure status
                Log.e(ContentValues.TAG, "Error querying workers: ", e)
            }
    }


}

data class Worker(
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = "",
    val imageUrl: String = ""
) {

}
