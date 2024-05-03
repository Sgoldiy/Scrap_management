package com.cpimca.scrapwala

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ConfirmScrapServiceViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _ConfirmScrapRequests = MutableLiveData<List<ConfirmScrapRequest>>() // Fix the type here
    val ConfirmScrapRequests: LiveData<List<ConfirmScrapRequest>> = _ConfirmScrapRequests
    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    // Function to fetch confirmed requests data
    fun fetchConfirmScrapRequests() {
        _isFetching.postValue(true)
        // Implement fetching confirmed requests from Firestore or any other data source
        // For example:
        firestore.collection("confirm_scrap_service_pickup")
            .get()
            .addOnSuccessListener { documents ->
                val ConfirmScrapRequestsList = mutableListOf<ConfirmScrapRequest>()
                for (document in documents) {
                    val userName = document.getString("userName") ?: ""
                    val phoneNumber = document.getString("phoneNumber") ?: ""
                    val email = document.getString("email") ?: ""
                    val address = document.getString("address") ?: ""
                    val iteminKG = document.getString("iteminKG")?: ""
                    val selectedTime = document.getString("selectedTime")?: ""


                    // Create a ConfirmScrapRequest object
                    val ConfirmScrapRequest = ConfirmScrapRequest(userName, phoneNumber, email, address , iteminKG , selectedTime)
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
    }
}

data class ConfirmScrapRequest(
    val userName: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val iteminKG: String?,
    val selectedTime: String?,
)