package com.cpimca.scrapwala

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmCategoryServiceUserViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _ConfirmCetegoryPickupRequests =
        MutableLiveData<List<ConfirmCetegoryPickupRequest>>()
    val ConfirmCetegoryPickupRequests: LiveData<List<ConfirmCetegoryPickupRequest>> =
        _ConfirmCetegoryPickupRequests
    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    // Function to fetch confirmed requests data for the current user
    fun fetchConfirmCetegoryPickupRequestsForCurrentUser() {
        _isFetching.postValue(true)
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        // Check if the current user is authenticated
        if (currentUserEmail != null) {
            firestore.collection("confirm_category_pickup")
                .whereEqualTo("email", currentUserEmail)
                .get()
                .addOnSuccessListener { documents ->
                    val ConfirmCetegoryPickupRequestsList =
                        mutableListOf<ConfirmCetegoryPickupRequest>()
                    for (document in documents) {
                        val userName = document.getString("userName") ?: ""
                        val phoneNumber = document.getString("phoneNumber") ?: ""
                        val email = document.getString("email") ?: ""
                        val address = document.getString("address") ?: ""
                        val selectedItem = document.getString("selectedItem") ?: ""
                        val selectedTime = document.getString("selectedTime") ?: ""

                        // Create a ConfirmCetegoryPickupRequest object
                        val ConfirmCetegoryPickupRequest = ConfirmCetegoryPickupRequest(
                            userName,
                            phoneNumber,
                            email,
                            address,
                            selectedItem,
                            selectedTime
                        )
                        ConfirmCetegoryPickupRequestsList.add(ConfirmCetegoryPickupRequest)
                    }
                    _ConfirmCetegoryPickupRequests.postValue(ConfirmCetegoryPickupRequestsList)
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