package com.cpimca.scrapwala

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class complainClassViewModel: ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _complains = MutableLiveData<List<Complain>>() // Fix the type here
    val complains: LiveData<List<Complain>> = _complains
    private val _isFetching = MutableLiveData<Boolean>()
    val isFetching: LiveData<Boolean> = _isFetching

    // Function to fetch complains data
    fun fetchcomplains() {
        _isFetching.postValue(true)
        // Implement fetching complains from Firestore or any other data source
        // For example:
        firestore.collection("complain")
            .get()
            .addOnSuccessListener { documents ->
                val complainsList = mutableListOf<Complain>()
                for (document in documents) {
                    val complain = document.getString("complain") ?: ""
                    val userEmail = document.getString("userEmail") ?: ""

                    val complains = Complain(
                        complain, userEmail
                    )
                    complainsList.add(complains)
                }
                _complains.postValue(complainsList)
                _isFetching.postValue(false)
            }
            .addOnFailureListener { exception ->
                // Handle error
                // Set isFetching to false in case of failure
                _isFetching.postValue(false)
            }
    }
}
data class Complain(
    val complain: String,
    val userEmail: String,
)