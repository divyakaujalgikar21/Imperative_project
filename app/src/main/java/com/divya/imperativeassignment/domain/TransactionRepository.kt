package com.divya.imperativeassignment.domain

import com.divya.imperativeassignment.Data.SecurePrefs
import com.divya.imperativeassignment.model.LoginRequest
import com.divya.imperativeassignment.remote.ApiService


class TransactionRepository(
    private val apiService: ApiService,
    private val securePrefs: SecurePrefs
) {
    suspend fun login(username: String, password: String): String {
        val response = apiService.login(LoginRequest(username, password))
        securePrefs.saveToken(response.token)
        return response.token
    }

    suspend fun getTransactions() = apiService.getTransactions()

    fun getToken() = securePrefs.getToken()
    fun clearToken() = securePrefs.clearToken()
}