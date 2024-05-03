package com.cpimca.scrapwala

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private var sharedPreferences: SharedPreferences? = null // Initialize as nullable

    var isLoggedIn by mutableStateOf(false)
    var isAdmin by mutableStateOf(false)

    fun initSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
        isLoggedIn = sharedPreferences?.getBoolean("isLoggedIn", false) ?: false
        isAdmin = sharedPreferences?.getBoolean("isAdmin", false) ?: false
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        this.isLoggedIn = isLoggedIn
        sharedPreferences?.edit()?.putBoolean("isLoggedIn", isLoggedIn)?.apply()
    }

    fun saveAdminStatus(isAdmin: Boolean) {
        this.isAdmin = isAdmin
        sharedPreferences?.edit()?.putBoolean("isAdmin", isAdmin)?.apply()
    }


}