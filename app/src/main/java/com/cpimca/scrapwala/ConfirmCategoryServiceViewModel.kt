package com.cpimca.scrapwala

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmCategoryServiceViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _ConfirmCetegoryPickupRequests = MutableLiveData<List<ConfirmCetegoryPickupRequest>>() // Fix the type here
    val ConfirmCetegoryPickupRequests: LiveData<List<ConfirmCetegoryPickupRequest>> = _ConfirmCetegoryPickupRequests
    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    // Function to fetch confirmed requests data
    fun fetchConfirmCetegoryPickupRequests() {
        _isFetching.postValue(true)
        // Implement fetching confirmed requests from Firestore or any other data source
        // For example:
        firestore.collection("confirm_category_pickup")
            .get()
            .addOnSuccessListener { documents ->
                val ConfirmCetegoryPickupRequestsList = mutableListOf<ConfirmCetegoryPickupRequest>()
                for (document in documents) {
                    val userName = document.getString("userName") ?: ""
                    val phoneNumber = document.getString("phoneNumber") ?: ""
                    val email = document.getString("email") ?: ""
                    val address = document.getString("address") ?: ""
                    val selectedItem = document.getString("selectedItem")?: ""
                    val selectedTime = document.getString("selectedTime")?: ""


                    // Create a ConfirmCetegoryPickupRequest object
                    val ConfirmCetegoryPickupRequest = ConfirmCetegoryPickupRequest(userName, phoneNumber, email, address , selectedItem , selectedTime)
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
    }
}

data class ConfirmCetegoryPickupRequest(
    val userName: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val selectedItem: String?,
    val selectedTime: String?,
)