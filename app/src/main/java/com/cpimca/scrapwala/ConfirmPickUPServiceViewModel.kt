package com.cpimca.scrapwala


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmPickUPServiceViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _ConfirmPickupRequests = MutableLiveData<List<ConfirmPickupRequest>>() // Fix the type here
    val ConfirmPickupRequests: LiveData<List<ConfirmPickupRequest>> = _ConfirmPickupRequests
    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    // Function to fetch confirmed requests data
    fun fetchConfirmPickupRequests() {
        _isFetching.postValue(true)
        // Implement fetching confirmed requests from Firestore or any other data source
        // For example:
        firestore.collection("confirm_pickup")
            .get()
            .addOnSuccessListener { documents ->
                val ConfirmPickupRequestsList = mutableListOf<ConfirmPickupRequest>()
                for (document in documents) {
                    val userName = document.getString("userName") ?: ""
                    val phoneNumber = document.getString("phoneNumber") ?: ""
                    val email = document.getString("email") ?: ""
                    val address = document.getString("address") ?: ""
                    val selectedNumber = document.getString("selectedNumber")?: ""
                    val selectedTime = document.getString("selectedTime")?: ""


                    // Create a ConfirmPickupRequest object
                    val ConfirmPickupRequest = ConfirmPickupRequest(userName, phoneNumber, email, address , selectedNumber , selectedTime)
                    ConfirmPickupRequestsList.add(ConfirmPickupRequest)
                }
                _ConfirmPickupRequests.postValue(ConfirmPickupRequestsList)
                _isFetching.postValue(false)
            }
            .addOnFailureListener { exception ->
                // Handle error
                // Set isFetching to false in case of failure
                _isFetching.postValue(false)
            }
    }
}

data class ConfirmPickupRequest(
    val userName: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val selectedNumber: String?,
    val selectedTime: String?,
)