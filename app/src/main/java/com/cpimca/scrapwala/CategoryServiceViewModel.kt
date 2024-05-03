package com.cpimca.scrapwala

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CategoryServiceViewModel : ViewModel() {

    private val _requests = MutableLiveData<List<CategoryServiceRequest>>()
    val requests: LiveData<List<CategoryServiceRequest>> = _requests

    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    private val firestore = Firebase.firestore
    private val auth = Firebase.auth


    @OptIn(DelicateCoroutinesApi::class)
    fun fetchPickupRequests() {
        _isFetching.value = true
        GlobalScope.launch {
            try {
                val fetchedRequests = CategoryServiceRepository().getCategoryServiceRequests()
                _requests.postValue(fetchedRequests)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isFetching.postValue(false)
            }
        }
    }

    fun storeRequest(
        address: String,
        selectedItem: String,
        selectedTime: String,
    ) {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid
        val email = currentUser?.email

        // Fetch user's name and phone number from Firestore
        firestore.collection("users").document(userId!!).get()
            .addOnSuccessListener { documentSnapshot ->
                val userName = documentSnapshot.getString("name")
                val phoneNumber = documentSnapshot.getString("phone")

                // Construct request object
                val request = hashMapOf(
                    "userId" to userId,
                    "email" to email,
                    "userName" to userName,
                    "phoneNumber" to phoneNumber,
                    "address" to address,
                    "selectedItem" to selectedItem,
                    "selectedTime" to selectedTime,
                    "timestamp" to FieldValue.serverTimestamp()
                )

                // Add request to Firestore
                firestore.collection("requestForMonthlyCategoryService").add(request)
                    .addOnSuccessListener { documentReference ->
                        // Successfully added document

                    }.addOnFailureListener { e ->
                        // Failed to add document
                    }
            }.addOnFailureListener { e ->
                // Failed to fetch user data
            }
    }

    fun removeFromCurrentCollection(request: CategoryServiceRequest) {
        val db = Firebase.firestore
        val currentRequestsRef = db.collection("requestForMonthlyCategoryService")

        // Query the collection to find the document that matches the request
        currentRequestsRef.whereEqualTo("userId", request.userId)
            .whereEqualTo("email", request.email)
            .whereEqualTo("userName", request.userName)
            .whereEqualTo("address", request.address)
            .whereEqualTo("selectedItem", request.selectedItem)
            .whereEqualTo("selectedTime", request.selectedTime)
            .whereEqualTo("timestamp", request.timestamp).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    // Delete the document
                    document.reference.delete().addOnSuccessListener {
                        // Document successfully deleted
                        Log.d(
                            "CleaningServiceViewModel",
                            "Document deleted from requestForMonthlyCategoryService"
                        )
                    }.addOnFailureListener { e ->
                        // Handle failure
                        Log.e(
                            "CleaningServiceViewModel",
                            "Error deleting document from requestForMonthlyCategoryService: ${e.message}",
                            e
                        )
                    }
                }
            }.addOnFailureListener { e ->
                // Handle failure
                Log.e(
                    "CleaningServiceViewModel",
                    "Error querying documents for deletion: ${e.message}",
                    e
                )
            }
    }

}

class CategoryServiceRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getCategoryServiceRequests(): List<CategoryServiceRequest> {
        // Fetch data from Firestore
        val snapshot = firestore.collection("requestForMonthlyCategoryService").get().await()

        // Convert Firestore data to list of CategoryServiceRequest objects
        return snapshot.documents.map { document ->
            val userId = document.getString("userId")
            val email = document.getString("email")
            val userName = document.getString("userName") // Retrieve userName
            val phoneNumber = document.getString("phoneNumber")
            val address = document.getString("address")
            val selectedItem = document.getString("selectedItem")
            val selectedTime = document.getString("selectedTime")
            val timestamp = document.getTimestamp("timestamp")
            val documentId = document.id
            CategoryServiceRequest(
                userId,
                email,
                userName,
                phoneNumber,
                address,
                selectedItem,
                selectedTime,
                timestamp,
                documentId,
            )
        }
    }
}

data class CategoryServiceRequest(
    val userId: String?,
    val email: String?,
    val userName: String?, // Add userName field
    val phoneNumber: String?,
    val address: String?,
    val selectedItem: String?,
    val selectedTime: String?,
    val timestamp: com.google.firebase.Timestamp?,
    val documentId: String,
    var isConfirmed: Boolean = false, // Add isConfirmed property
)