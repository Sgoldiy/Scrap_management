package com.cpimca.scrapwala

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmScrapServiceUserViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _ConfirmScrapRequests = MutableLiveData<List<ConfirmScrapRequest>>()
    val ConfirmScrapRequests: LiveData<List<ConfirmScrapRequest>> = _ConfirmScrapRequests
    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    // Function to fetch confirmed requests data for the current user
    fun fetchConfirmScrapRequestsForCurrentUser() {
        _isFetching.postValue(true)
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        // Check if the current user is authenticated
        if (currentUserEmail != null) {
            firestore.collection("confirm_scrap_service_pickup")
                .whereEqualTo("email", currentUserEmail)
                .get()
                .addOnSuccessListener { documents ->
                    val ConfirmScrapRequestsList = mutableListOf<ConfirmScrapRequest>()
                    for (document in documents) {
                        val userName = document.getString("userName") ?: ""
                        val phoneNumber = document.getString("phoneNumber") ?: ""
                        val email = document.getString("email") ?: ""
                        val address = document.getString("address") ?: ""
                        val selectedNumber = document.getString("selectedNumber")?: ""
                        val selectedTime = document.getString("selectedTime")?: ""

                        // Create a ConfirmScrapRequest object
                        val ConfirmScrapRequest = ConfirmScrapRequest(userName, phoneNumber, email, address , selectedNumber , selectedTime)
                        ConfirmScrapRequestsList.add(ConfirmScrapRequest)
                    }
                    _ConfirmScrapRequests.postValue(ConfirmScrapRequestsList)
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