package com.cpimca.scrapwala

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmPickupServiceUserViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _ConfirmPickupRequests = MutableLiveData<List<ConfirmPickupRequest>>()
    val ConfirmPickupRequests: LiveData<List<ConfirmPickupRequest>> = _ConfirmPickupRequests
    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    // Function to fetch confirmed requests data for the current user
    fun fetchConfirmPickupRequestsForCurrentUser() {
        _isFetching.postValue(true)
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        // Check if the current user is authenticated
        if (currentUserEmail != null) {
            firestore.collection("confirm_pickup")
                .whereEqualTo("email", currentUserEmail)
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
        } else {
            // If user is not authenticated, set isFetching to false
            _isFetching.postValue(false)
        }
    }
}